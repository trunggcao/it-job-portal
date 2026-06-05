package com.example.itjobportal.entity;

import com.example.itjobportal.enums.EUserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name ="tbl_users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    @Column(unique = true)
    private String email;
    private String password;
    private String profileImageUrl;
    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    private Boolean isActive;
    private String activationToken;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private EUserRole role;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    List<Resume> resumes;

    @PrePersist
    public void prePersist(){
        if (this.isActive == null){
            isActive = false;
        }
        if (this.role == null) {
            this.role = EUserRole.ROLE_CANDIDATE;
        }
    }
}
