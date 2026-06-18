package com.example.itjobportal.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_profile_unlocks", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"employer_id", "profile_id"})
})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileUnlock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employer_id", nullable = false)
    private Long employerId; // ID của User mang role ROLE_EMPLOYER

    @Column(name = "profile_id", nullable = false)
    private Long profileId;  // ID của CandidateProfile được mở khóa

    @CreationTimestamp
    private LocalDateTime unlockedAt;
}
