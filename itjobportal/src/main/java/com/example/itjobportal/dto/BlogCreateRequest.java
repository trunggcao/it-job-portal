package com.example.itjobportal.dto;

import lombok.Data;

@Data
public class BlogCreateRequest {
    private String title;
    private String content;
    private Long categoryId;
    private boolean published;
    private String image;
}
