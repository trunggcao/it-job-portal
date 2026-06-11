package com.example.itjobportal.repository;

import com.example.itjobportal.entity.CompanyVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyVerificationRepository extends JpaRepository<CompanyVerification,Long> {
    List<CompanyVerification> findByCompanyId(Long id);
}
