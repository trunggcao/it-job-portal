package com.example.itjobportal.repository;

import com.example.itjobportal.entity.CandidateProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CandidateProfileRepository extends JpaRepository<CandidateProfile, Long> {
    Optional<CandidateProfile> findByUserId(Long userId);

    @Query("SELECT DISTINCT cp FROM CandidateProfile cp " +
            "LEFT JOIN cp.skills s " +
            "WHERE cp.isOpenToWork = true " +
            "AND (:title IS NULL OR LOWER(cp.title) LIKE LOWER(CONCAT('%', :title, '%'))) " +
            "AND (:skillIds IS NULL OR s.id IN :skillIds)")
    List<CandidateProfile> searchCandidates(@Param("title") String title,
                                            @Param("skillIds") List<Long> skillIds);
}
