package com.example.itjobportal.controller;

import com.example.itjobportal.entity.Blog;
import com.example.itjobportal.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/blogs")
public class BlogController {
    private final BlogService blogService;

    @GetMapping
    public ResponseEntity<List<Blog>> getPublishedBlogs() {
        return ResponseEntity.status(HttpStatus.OK).body(blogService.getPublishedBlogs());
    }

    // GET BLOG DETAIL
    @GetMapping("/{id}")
    public ResponseEntity<Blog> getBlogDetail(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(blogService.getPublishedBlogDetail(id));
    }

    // GET BLOG BY CATEGORY
    @GetMapping("/category/{id}")
    public ResponseEntity<List<Blog>> getBlogsByCategory(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(blogService.getPublishedBlogsByCategory(id));
    }
    @GetMapping("/search")
    public ResponseEntity<List<Blog>> searchBlogs(
            @RequestParam String keyword
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(blogService.searchPublishedBlogs(null,keyword));
    }

    //SEARCH BY CATEGORY
    @GetMapping("/category/{id}/search")
    public ResponseEntity<List<Blog>> searchBlogsByCategory(
            @PathVariable Long id,
            @RequestParam String keyword
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(blogService.searchPublishedBlogs(id,keyword));
    }
}
