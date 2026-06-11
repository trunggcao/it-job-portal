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
public class CompanyVerificationDTO {
    private Long id;
    private String status;
    private String taxCode;
    private String businessLicenseUrl;
    private String rejectReason;
    private Long companyId;
    private String companyName;
    private Long employerId;
    private String employerName;
    private LocalDateTime createdAt;
}
