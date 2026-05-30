package com.example.itjobportal.repository;

import com.example.itjobportal.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkilllRepository extends JpaRepository<Skill,Long> {
    List<Skill> findByNameContainingIgnoreCase(String name);
}
