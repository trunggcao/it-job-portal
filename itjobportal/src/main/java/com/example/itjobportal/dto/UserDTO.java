package com.example.itjobportal.dto;


import com.example.itjobportal.enums.EUserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    private Long id;
    private String fullName;
    private String email;
    private String password;
    private String profileImageUrl;
    private EUserRole role;
    private Long companyId;
    private boolean companyIsAtive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
