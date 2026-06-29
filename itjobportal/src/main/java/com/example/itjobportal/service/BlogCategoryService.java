package com.example.itjobportal.service;

import com.example.itjobportal.entity.BlogCategory;
import com.example.itjobportal.repository.BlogCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogCategoryService {

    private final BlogCategoryRepository categoryRepository;

    public List<BlogCategory> getAllCategories() {

        return categoryRepository.findAll();
    }
}
