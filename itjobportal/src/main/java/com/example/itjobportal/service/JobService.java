package com.example.itjobportal.service;

import com.example.itjobportal.dto.JobDTO;
import com.example.itjobportal.dto.SkillDTO;
import com.example.itjobportal.entity.Company;
import com.example.itjobportal.entity.Job;
import com.example.itjobportal.entity.Skill;
import com.example.itjobportal.enums.EJobLevel;
import com.example.itjobportal.repository.CompanyRepository;
import com.example.itjobportal.repository.JobRepository;
import com.example.itjobportal.repository.SkilllRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;
    private final CompanyRepository companyRepository;
    private final SkilllRepository skilllRepository;

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

    public JobDTO createJob(JobDTO jobDTO){
        Company company = companyRepository.findById(jobDTO.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company is not found"));

        List<Long> skillIds = jobDTO.getSkills().stream()
                .map(SkillDTO::getId)
                .toList();

        List<Skill> skills = skilllRepository.findAllById(skillIds);

        Job job = toEntity(jobDTO,company,skills);
        Job savedJob = jobRepository.save(job);

        return toDTO(savedJob);
    }

    public JobDTO updateJob(Long id, JobDTO jobDTO){
        Job existingJob = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job is not found"));

        Company company = companyRepository.findById(jobDTO.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company is not found"));

        List<Long> skillIds = jobDTO.getSkills().stream()
                .map(SkillDTO::getId)
                .toList();

        List<Skill> skills = skilllRepository.findAllById(skillIds);

        existingJob.setName(jobDTO.getName());
        existingJob.setLocation(jobDTO.getLocation());
        existingJob.setSalary(jobDTO.getSalary());
        existingJob.setLevel(EJobLevel.valueOf(jobDTO.getLevel()));
        existingJob.setDescription(jobDTO.getDescription());
        existingJob.setStartDate(jobDTO.getStartDate());
        existingJob.setEndDate(jobDTO.getEndDate());
        existingJob.setCompany(company);
        existingJob.setSkills(skills);

        return toDTO(existingJob);
    }

    public List<JobDTO> getAllJob(){
        List<Job> jobs = jobRepository.findAll();
        return jobs.stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional
    public void deleteJob(Long id){
        if (!jobRepository.existsById(id)){
            throw new RuntimeException("job with id: "+ id + " is not found to delete");
        }
        jobRepository.deleteById(id);
    }

    public List<JobDTO> findByName(String keyword){
        List<Job> jobs = jobRepository.findByNameContainingIgnoreCase(keyword);

         return jobs.stream()
                 .map(this::toDTO)
                 .toList();
    }

    public Job findById(Long id){
        return jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job with id:" + id + " is not found"));
    }

}
