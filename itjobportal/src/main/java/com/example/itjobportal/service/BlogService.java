package com.example.itjobportal.service;

import com.example.itjobportal.dto.BlogCreateRequest;
import com.example.itjobportal.dto.BlogUpdateRequest;
import com.example.itjobportal.entity.Blog;
import com.example.itjobportal.entity.BlogCategory;
import com.example.itjobportal.repository.BlogCategoryRepository;
import com.example.itjobportal.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;
    private final BlogCategoryRepository categoryRepository;


    public Blog createBlog(BlogCreateRequest request, String imageUrl) {
        BlogCategory category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Blog blog = new Blog();
        blog.setTitle(request.getTitle());
        blog.setContent(request.getContent());

        // imageUrl lúc này là link từ Cloudinary (ví dụ: https://res.cloudinary.com/...)
        blog.setImage(imageUrl);

        blog.setCategory(category);
        blog.setPublished(false);
        blog.setTime(LocalDateTime.now().toString());

        return blogRepository.save(blog);
    }

    public Blog updateBlog(Long id, BlogUpdateRequest request, String imageUrl) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog not found"));

        if (request.getTitle() != null) blog.setTitle(request.getTitle());
        if (request.getContent() != null) blog.setContent(request.getContent());

        if (imageUrl != null && !imageUrl.isEmpty()) {
            blog.setImage(imageUrl);
        }

        if (request.getCategoryId() != null) {
            BlogCategory category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            blog.setCategory(category);
        }

        if (request.getPublished() != null) {
            blog.setPublished(request.getPublished());
        }

        return blogRepository.save(blog);
    }

    public void deleteBlog(Long id) {
        if (!blogRepository.existsById(id)) {
            throw new RuntimeException("Blog not found");
        }
        blogRepository.deleteById(id);
    }

    public Blog getBlogById(Long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog not found"));
    }

    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    public Blog publishBlog(Long id, boolean published) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog not found"));
        blog.setPublished(published);
        return blogRepository.save(blog);
    }

    public List<Blog> getBlogsByCategory(Long categoryId) {
        return blogRepository.findByCategoryId(categoryId);
    }

    public List<Blog> getPublishedBlogs() {
        return blogRepository.findByPublishedTrueOrderByTimeDesc();
    }

    public List<Blog> getPublishedBlogsByCategory(Long categoryId) {
        return blogRepository.findByCategoryIdAndPublishedTrue(categoryId);
    }

    public Blog getPublishedBlogDetail(Long id) {
        Blog blog = blogRepository.findByIdAndPublishedTrue(id);
        if (blog == null) {
            throw new RuntimeException("Blog not found or unpublished");
        }
        return blog;
    }

    public List<Blog> searchPublishedBlogs(Long categoryId, String keyword) {
        return blogRepository.searchPublishedBlogs(categoryId, keyword);
    }
}
