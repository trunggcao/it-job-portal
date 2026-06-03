package com.example.itjobportal.service;

import com.example.itjobportal.dto.ResumeDTO;
import com.example.itjobportal.entity.Job;
import com.example.itjobportal.entity.Resume;
import com.example.itjobportal.entity.User;
import com.example.itjobportal.enums.Estatus;
import com.example.itjobportal.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final JobService jobService;
    private final UserService userService;

    private Resume toEntity(ResumeDTO resumeDTO, User user, Job job){
        return Resume.builder()
                .email(user.getEmail())
                .url(resumeDTO.getUrl())
                .status(Estatus.valueOf(resumeDTO.getStatus()))
                .user(user)
                .job(job)
                .build();
    }

    private ResumeDTO toDTO(Resume resume){
        return ResumeDTO.builder()
                .id(resume.getId())
                .email(resume.getEmail())
                .url(resume.getUrl())
                .status(resume.getStatus().name())
                .userId(resume.getUser().getId())
                .userName(resume.getUser().getFullName())
                .jobId(resume.getJob().getId())
                .jobName(resume.getJob().getName())
                .createdAt(resume.getCreatedAt())
                .updateAt(resume.getUpdatedAt())
                .build();
    }
}
