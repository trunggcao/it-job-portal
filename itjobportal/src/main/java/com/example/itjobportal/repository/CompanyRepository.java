package com.example.itjobportal.repository;

import com.example.itjobportal.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company,Long> {
    List<Company> findByCompanyName(String companyName);
    List<Company> findByCompanyNameContainingIgnoreCase(String name);
    Optional<Company> findByEmployerId(Long id);


}
