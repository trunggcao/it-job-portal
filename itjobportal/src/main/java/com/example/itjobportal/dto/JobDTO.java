package com.example.itjobportal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobDTO {

    private Long id;
    private String name;
    private String location;
    private Double salary;
    private String level;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isActive;
    private Long companyId;
    private String companyName;
    private List<SkillDTO> skills;
}
