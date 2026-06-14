package com.example.itjobportal.service;

import com.example.itjobportal.dto.CompanyVerificationDTO;
import com.example.itjobportal.entity.Company;
import com.example.itjobportal.entity.CompanyVerification;
import com.example.itjobportal.enums.Estatus;
import com.example.itjobportal.repository.CompanyRepository;
import com.example.itjobportal.repository.CompanyVerificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CompanyVerificationService {

    private final CompanyVerificationRepository companyVerificationRepository;
    private final CompanyRepository companyRepository;

    public CompanyVerification toEntiy(CompanyVerificationDTO companyVerificationDTO ,Company company){

        Estatus finalStatus = companyVerificationDTO.getStatus() != null
                ? Estatus.valueOf(companyVerificationDTO.getStatus())
                : Estatus.PENDING;

        return CompanyVerification.builder()
                .id(companyVerificationDTO.getId())
                .status(finalStatus)
                .taxCode(companyVerificationDTO.getTaxCode())
                .businessLicenseUrl(companyVerificationDTO.getBusinessLicenseUrl())
                .rejectReason(companyVerificationDTO.getRejectReason())
                .company(company)
                .build();
    }

    public CompanyVerificationDTO toDTO(CompanyVerification companyVerification){
        return CompanyVerificationDTO.builder()
                .id(companyVerification.getId())
                .status(companyVerification.getStatus().name())
                .taxCode(companyVerification.getTaxCode())
                .businessLicenseUrl(companyVerification.getBusinessLicenseUrl())
                .rejectReason(companyVerification.getRejectReason())
                .createdAt(companyVerification.getCreatedAt())

                // company
                .companyId(companyVerification.getCompany().getId())
                .companyName(companyVerification.getCompany().getCompanyName())

                //employer
                .employerId(companyVerification.getCompany().getEmployer().getId())
                .employerName(companyVerification.getCompany().getEmployer().getFullName())
                .build();
    }

    public CompanyVerificationDTO createVerification(CompanyVerificationDTO companyVerificationDTO){

        if (companyVerificationDTO.getEmployerId() == null) {
            throw new RuntimeException("Employer is not found");
        }

        Company company = companyRepository.findById(companyVerificationDTO.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company is not found"));

        Long ownerId = company.getEmployer().getId();
        Long requesterId = companyVerificationDTO.getEmployerId();

        if (!Objects.equals(ownerId, requesterId)) {
            throw new RuntimeException("You have not permision to create verification");
        }

        CompanyVerification companyVerification = toEntiy(companyVerificationDTO , company);
        companyVerification.setStatus(Estatus.PENDING);
        CompanyVerification saved = companyVerificationRepository.save(companyVerification);

        return toDTO(saved);
    }

    @Transactional
    public CompanyVerificationDTO approveVerification(Long id){
        CompanyVerification companyVerification = companyVerificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Verification is not found"));

        Company company = companyRepository.findById(companyVerification.getCompany().getId())
                .orElseThrow( () -> new RuntimeException("Company is not found"));
        company.setActive(true);
        companyRepository.save(company);

        companyVerification.setStatus(Estatus.APPROVED);
        companyVerification = companyVerificationRepository.save(companyVerification);
        return toDTO(companyVerification);
    }

    @Transactional
    public CompanyVerificationDTO rejectVerification(Long id){
        CompanyVerification companyVerification = companyVerificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Verification is not found"));

        Company company = companyRepository.findById(companyVerification.getCompany().getId())
                .orElseThrow( () -> new RuntimeException("Company is not found"));
        company.setActive(false);
        companyRepository.save(company);

        companyVerification.setStatus(Estatus.REJECTED);
        companyVerification = companyVerificationRepository.save(companyVerification);
        return toDTO(companyVerification);
    }

    public List<CompanyVerificationDTO> getAll(){
        List<CompanyVerification> companyVerifications = companyVerificationRepository.findAll();
        return companyVerifications.stream()
                .map(this::toDTO)
                .toList();
    }

    public List<CompanyVerificationDTO> getHistory(Long id){
        Company company = companyRepository.findById(id)
                .orElseThrow( () -> new RuntimeException("Company is not found"));

        List<CompanyVerification> companyVerifications = companyVerificationRepository.findByCompanyId(id);
        return companyVerifications.stream()
                .map(this::toDTO)
                .toList();
    }
}
