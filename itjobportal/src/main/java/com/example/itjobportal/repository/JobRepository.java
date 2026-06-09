package com.example.itjobportal.repository;

import com.example.itjobportal.entity.Company;
import com.example.itjobportal.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByNameContainingIgnoreCase(String name);
    List<Job> findByCompany(Company company);
    List<Job> findByCompanyId(Long id);
}
