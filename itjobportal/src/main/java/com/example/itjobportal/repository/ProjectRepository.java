package com.example.itjobportal.repository;

import com.example.itjobportal.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByCandidateProfileId(Long profileId);
}
