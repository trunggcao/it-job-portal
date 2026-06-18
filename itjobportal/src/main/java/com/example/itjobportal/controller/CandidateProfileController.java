package com.example.itjobportal.controller;

import com.example.itjobportal.dto.CandidateProfileDTO;
import com.example.itjobportal.dto.CandidateSearchResponseDTO;
import com.example.itjobportal.entity.CandidateProfile;
import com.example.itjobportal.service.CandidateProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/candidate/profiles")
public class CandidateProfileController {
    private final CandidateProfileService candidateProfileService;

    @PostMapping("/save/{userId}")
    public ResponseEntity<CandidateProfileDTO> saveOrUpdateProfile(@PathVariable Long userId,
                                                                   @RequestBody CandidateProfileDTO dto){
        CandidateProfileDTO savedProfile = candidateProfileService.saveOrUpdateProfile(userId,dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProfile);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CandidateProfileDTO> getProfileByUserId(@PathVariable Long userId) {
        var profile = candidateProfileService.getProfileByUserId(userId);

        CandidateProfileDTO profileDTO = candidateProfileService.toDTO(profile);

        return ResponseEntity.ok(profileDTO);
    }

    @GetMapping("/search")
    public ResponseEntity<List<CandidateSearchResponseDTO>> searchCandidates(
            @RequestParam Long employerId, // ID của Nhà tuyển dụng đang thực hiện tìm kiếm
            @RequestParam(required = false) String title,
            @RequestParam(required = false) List<Long> skillIds) {

        List<CandidateSearchResponseDTO> results = candidateProfileService.searchCandidatesForEmployer(employerId, title, skillIds);
        return ResponseEntity.ok(results);
    }

    @PostMapping("/unlock")
    public ResponseEntity<Map<String, String>> unlockProfile(
            @RequestParam Long employerId,
            @RequestParam Long profileId) {

        candidateProfileService.unlockProfile(employerId, profileId);

        return ResponseEntity.ok(Map.of("message", "unclock infor success"));
    }
}
