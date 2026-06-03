package com.example.itjobportal.entity;

import com.example.itjobportal.enums.EJobLevel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tbl_jobs")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String location;
    private Double salary;
    @Enumerated(EnumType.STRING)
    private EJobLevel level;
    @Column(columnDefinition = "MEDIUMTEXT")
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "job_skill",joinColumns = @JoinColumn(name = "job_id"),
    inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private List<Skill> skills;

    @OneToMany(mappedBy = "job",fetch = FetchType.LAZY)
    List<Resume> resumes;

}
