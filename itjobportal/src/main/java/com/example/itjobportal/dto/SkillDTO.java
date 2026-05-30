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
public class SkillDTO {

    private Long id;
    private String name;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}
