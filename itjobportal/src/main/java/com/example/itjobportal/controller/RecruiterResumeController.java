package com.example.itjobportal.controller;

import com.example.itjobportal.dto.ResumeDTO;
import com.example.itjobportal.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recruiter/resumes")
public class RecruiterResumeController {

    private final ResumeService resumeService;

    @PutMapping("/{id}/status")
    public ResponseEntity<ResumeDTO> updateStatus(@PathVariable Long id,
            @RequestParam("status") String status) {

        ResumeDTO updatedResume = resumeService.updateResumeStatus(id, status);
        return ResponseEntity.status(HttpStatus.OK).body(updatedResume);
    }

    @GetMapping("/jobs/{jobId}")
    public ResponseEntity<List<ResumeDTO>> findAllByJobId(@PathVariable Long jobId){
        List<ResumeDTO> resumes = resumeService.getAllByJobId(jobId);
        return ResponseEntity.status(HttpStatus.OK).body(resumes);
    }
}
