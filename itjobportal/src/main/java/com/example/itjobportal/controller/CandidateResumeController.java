package com.example.itjobportal.controller;


import com.example.itjobportal.dto.ResumeDTO;
import com.example.itjobportal.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/candidate/resumes")
public class CandidateResumeController {

    private final ResumeService resumeService;

    @PostMapping("/apply/jobs/{jobId}")
    public ResponseEntity<ResumeDTO> applyJob(
            @PathVariable Long jobId,
            @RequestBody ResumeDTO resumeDTO) {

        ResumeDTO createdResume = resumeService.createResume( resumeDTO, jobId);

      return ResponseEntity.status(HttpStatus.CREATED).body(createdResume);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResumeDTO> findById(@PathVariable Long id){
        ResumeDTO resume = resumeService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(resume);
    }

    @GetMapping("/my-apply")
    public  ResponseEntity<List<ResumeDTO>> findByCurrentUserId(){
        List<ResumeDTO> resumes = resumeService.getByCurrentUserId();
        return ResponseEntity.status(HttpStatus.OK).body(resumes);
    }
}
