package com.example.itjobportal.service;


import com.example.itjobportal.dto.CompanyDTO;
import com.example.itjobportal.entity.Company;
import com.example.itjobportal.entity.User;
import com.example.itjobportal.enums.EUserRole;
import com.example.itjobportal.repository.CompanyRepository;
import com.example.itjobportal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    public Company toEnity(CompanyDTO companyDTO){
        Company company = Company.builder()
                .id(companyDTO.getId())
                .companyName(companyDTO.getCompanyName())
                .address(companyDTO.getAddress())
                .description(companyDTO.getDescription())
                .website(companyDTO.getWebsite())
                .logoUrl(companyDTO.getLogoUrl())
                .createdAt(companyDTO.getCreatedAt())
                .updatedAt(companyDTO.getUpdatedAt())
                .build();
        if (companyDTO.getEmployerId() != null) {
            User employer = userRepository.findById(companyDTO.getEmployerId()).orElse(null);
            company.setEmployer(employer);
        }
        return company;
    }

    public CompanyDTO toDTO(Company company){
        return CompanyDTO.builder()
                .id(company.getId())
                .companyName(company.getCompanyName())
                .address(company.getAddress())
                .description(company.getDescription())
                .website(company.getWebsite())
                .logoUrl(company.getLogoUrl())
                .employerId(company.getEmployer() != null ? company.getEmployer().getId() : null)
                .createdAt(company.getCreatedAt())
                .updatedAt(company.getUpdatedAt())
                .build();
    }

    public CompanyDTO createCompany(CompanyDTO companyDTO){
        if (companyDTO.getEmployerId() == null) {
            throw new RuntimeException("Yêu cầu thông tin ID nhà tuyển dụng!");
        }
        User employer = userRepository.findById(companyDTO.getEmployerId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản nhà tuyển dụng!"));
        if (employer.getRole() != EUserRole.ROLE_EMPLOYER) {
            throw new RuntimeException("Tài khoản của bạn không có quyền đăng ký thông tin công ty!");
        }
        if (employer.getCompany() != null) {
            throw new RuntimeException("Mỗi tài khoản nhà tuyển dụng chỉ được phép quản lý duy nhất 1 công ty!");
        }
        Company newCompany = toEnity(companyDTO);
        newCompany.setEmployer(employer);
        newCompany = companyRepository.save(newCompany);

        return toDTO(newCompany);
    }

    public List<CompanyDTO> getAllCompanies(){
        List<Company> companies = companyRepository.findAll();
        return companies.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public CompanyDTO getCompanyById(Long companyId){
        Company company = companyRepository.findById(companyId)
                .orElseThrow(()-> new RuntimeException("Company not found")) ;
        return toDTO(company);
    }

    public CompanyDTO updateCompany(Long companyId, CompanyDTO companyDTO){
        Company existingCompany = companyRepository.findById(companyId)
                .orElseThrow(()-> new RuntimeException("Company not found")) ;
        existingCompany.setCompanyName(companyDTO.getCompanyName());
        existingCompany.setAddress(companyDTO.getAddress());
        existingCompany.setWebsite(companyDTO.getWebsite());
        existingCompany.setLogoUrl(companyDTO.getLogoUrl());
        existingCompany.setDescription(companyDTO.getDescription());

        existingCompany = companyRepository.save(existingCompany);
        return toDTO(existingCompany);
    }

    public void deleteCompany(Long companyId){
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found"));
        companyRepository.delete(company);
    }

    public List<CompanyDTO> findCompanyByNameIgnoreCase(String keyword){
        List<Company> companies =
                companyRepository.findByCompanyNameContainingIgnoreCase(keyword);

        return companies.stream()
                .map(this::toDTO)
                .toList();
    }


}
