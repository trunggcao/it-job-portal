package com.example.itjobportal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CandidateSearchResponseDTO {
    private Long profileId;
    private String title;
    private Integer yearsOfExperience;
    private List<String> skills;
    private List<ProjectDTO> projects;


    private String fullName;
    private String email;

    private boolean isUnlocked;
}
