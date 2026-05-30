package com.example.itjobportal.service;

import com.example.itjobportal.dto.JobDTO;
import com.example.itjobportal.dto.SkillDTO;
import com.example.itjobportal.entity.Company;
import com.example.itjobportal.entity.Job;
import com.example.itjobportal.entity.Skill;
import com.example.itjobportal.enums.EJobLevel;
import com.example.itjobportal.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;

    public Job toEntity(JobDTO jobDTO, Company company, List<Skill> skills){
        return Job.builder()
                .id(jobDTO.getId())
                .name(jobDTO.getName())
                .location(jobDTO.getLocation())
                .salary(jobDTO.getSalary())
                .level(EJobLevel.valueOf(jobDTO.getLevel()))
                .description(jobDTO.getDescription())
                .startDate(jobDTO.getStartDate())
                .endDate(jobDTO.getEndDate())
                .isActive(jobDTO.isActive())
                .company(company)
                .skills(skills)
                .build();
    }

    public JobDTO toDTO(Job job){
        return JobDTO.builder()
                .id(job.getId())
                .name(job.getName())
                .location(job.getLocation())
                .salary(job.getSalary())
                .level(job.getLevel().name())
                .description(job.getDescription())
                .startDate(job.getStartDate())
                .endDate(job.getEndDate())
                .isActive(job.isActive())

                // company
                .companyId(job.getCompany().getId())
                .companyName(job.getCompany().getCompanyName())

                //skills
                .skills(job.getSkills().stream()
                        .map(skill -> SkillDTO.builder()
                                .id(skill.getId())
                                .name(skill.getName())
                                .build())
                        .toList())
                .build();
    }
}
