package com.example.itjobportal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CandidateProfileDTO {
    private Long id;
    private String title;
    private Integer yearsOfExperience;
    private Boolean isOpenToWork;
    private List<SkillDTO> skills;
    private List<ProjectDTO> projects;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
}
