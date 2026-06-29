package com.example.itjobportal.repository;

import com.example.itjobportal.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog,Long> {
    List<Blog> findByCategoryId(Long categoryId);

    List<Blog> findByPublishedTrueOrderByTimeDesc();

    List<Blog> findByCategoryIdAndPublishedTrue(Long categoryId);

    Blog findByIdAndPublishedTrue(Long id);
    
    @Query("""
        SELECT b FROM Blog b
        WHERE b.published = true
        AND (:categoryId IS NULL OR b.category.id = :categoryId)
        AND (
            LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
        )
        ORDER BY b.time DESC
    """)
    List<Blog> searchPublishedBlogs(
            @Param("categoryId") Long categoryId,
            @Param("keyword") String keyword
    );
}
