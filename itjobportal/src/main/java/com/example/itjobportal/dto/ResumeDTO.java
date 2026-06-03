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
public class ResumeDTO {

    private Long id;
    private String email;
    private String url;
    private String status;
    private Long userId;
    private String userName;
    private Long jobId;
    private String jobName;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
}
