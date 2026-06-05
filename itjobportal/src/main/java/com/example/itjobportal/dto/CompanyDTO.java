package com.example.itjobportal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyDTO {
    private Long id;
    private String companyName;
    private String website;
    private String description;
    private String address;
    private String logoUrl;
    private Long employerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
