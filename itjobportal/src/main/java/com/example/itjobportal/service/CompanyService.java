package com.example.itjobportal.service;


import com.example.itjobportal.dto.CompanyDTO;
import com.example.itjobportal.entity.Company;
import com.example.itjobportal.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    public Company toEnity(CompanyDTO companyDTO){
        return Company.builder()
                .id(companyDTO.getId())
                .companyName(companyDTO.getCompanyName())
                .address(companyDTO.getAddress())
                .description(companyDTO.getDescription())
                .website(companyDTO.getWebsite())
                .logoUrl(companyDTO.getLogoUrl())
                .createdAt(companyDTO.getCreatedAt())
                .updatedAt(companyDTO.getUpdatedAt())
                .build();
    }

    public CompanyDTO toDTO(Company company){
        return CompanyDTO.builder()
                .id(company.getId())
                .companyName(company.getCompanyName())
                .address(company.getAddress())
                .description(company.getDescription())
                .website(company.getWebsite())
                .logoUrl(company.getLogoUrl())
                .createdAt(company.getCreatedAt())
                .updatedAt(company.getUpdatedAt())
                .build();
    }

    public CompanyDTO createCompany(CompanyDTO companyDTO){
        Company newCompany = toEnity(companyDTO);
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
