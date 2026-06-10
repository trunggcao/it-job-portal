package com.example.itjobportal.entity;


import com.example.itjobportal.enums.EJobLevel;
import com.example.itjobportal.enums.Estatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_company_verifications")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyVerification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Estatus status;
    private String taxCode;
    private String businessLicenseUrl;
    private String rejectReason;
    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;
}
