package com.example.itjobportal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_Blogs")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String content;
    private String time;
    private boolean published;
    private String image;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private BlogCategory category;

}
