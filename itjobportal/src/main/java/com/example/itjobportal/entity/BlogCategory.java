package com.example.itjobportal.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tbl_blog_categories")
public class BlogCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String categoryName;

    @OneToMany(mappedBy = "category")
    private List<Blog> blogs;
}
