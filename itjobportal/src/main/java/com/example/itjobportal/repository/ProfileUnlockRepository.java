package com.example.itjobportal.repository;

import com.example.itjobportal.entity.ProfileUnlock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileUnlockRepository extends JpaRepository<ProfileUnlock,Long> {
    boolean existsByEmployerIdAndProfileId(Long employerId, Long profileId);
}
