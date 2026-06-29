package com.example.itjobportal.controller;

import com.example.itjobportal.dto.BlogCreateRequest;
import com.example.itjobportal.dto.BlogUpdateRequest;
import com.example.itjobportal.entity.Blog;
import com.example.itjobportal.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/blogs/admin")
public class AdminBlogController {
    private final BlogService blogService;

    @PostMapping
    public ResponseEntity<Blog> createBlog(@RequestBody BlogCreateRequest request) {
        // request.getImage() bây giờ đã là URL (String) từ Cloudinary gửi về
        Blog created = blogService.createBlog(request, request.getImage());

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Blog> updateBlog(
            @PathVariable Long id,
            @RequestBody BlogUpdateRequest request) {

        // request.getImage() là URL ảnh mới (hoặc ảnh cũ) dạng String
        Blog updated = blogService.updateBlog(id, request, request.getImage());

        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    // DELETE BLOG
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBlog(@PathVariable Long id) {
        blogService.deleteBlog(id);
        return ResponseEntity.ok("Delete blog successfully");
    }

    // GET BLOG DETAIL
    @GetMapping("/{id}")
    public ResponseEntity<Blog> getBlogDetail(@PathVariable Long id) {
        Blog blog = blogService.getBlogById(id);
        return ResponseEntity.status(HttpStatus.OK).body(blog);
    }

    // GET ALL BLOGS
    @GetMapping
    public ResponseEntity<List<Blog>> getAllBlogs() {
        List<Blog> blogs = blogService.getAllBlogs();
        return ResponseEntity.status(HttpStatus.OK).body(blogs);
    }

    // PUBLISH / UNPUBLISH
    @PostMapping("/{id}/publish")
    public ResponseEntity<Blog> publishBlog(
            @PathVariable Long id,
            @RequestParam boolean published
    ) {
        Blog blog = blogService.publishBlog(id, published);
        return ResponseEntity.status(HttpStatus.OK).body(blog);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<Blog>> getBlogsByCategory(@PathVariable Long id) {
        List<Blog> blogs = blogService.getBlogsByCategory(id);
        return ResponseEntity.status(HttpStatus.OK).body(blogs);
    }

}
