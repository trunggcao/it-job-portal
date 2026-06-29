package com.example.itjobportal.dto;

import lombok.Data;

@Data
public class BlogUpdateRequest {

    private String title;
    private String content;
    private Long categoryId;
    private String image;
    private Boolean published;
}
