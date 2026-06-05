package com.example.itjobportal.repository;

import com.example.itjobportal.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
    List<Resume> findByJobId(Long id);
    List<Resume> findByUserId(Long id);
}
