package com.example.itjobportal.controller;

import com.example.itjobportal.entity.BlogCategory;
import com.example.itjobportal.service.BlogCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/blogs")
public class BlogCategoryController {
    private final BlogCategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<List<BlogCategory>> getAllCategories() {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getAllCategories());
    }
}
