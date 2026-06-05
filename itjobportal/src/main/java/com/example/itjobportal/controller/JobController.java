package com.example.itjobportal.controller;

import com.example.itjobportal.dto.JobDTO;
import com.example.itjobportal.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jobs")
public class JobController {

    private final JobService jobService;

    @PostMapping
    public ResponseEntity<JobDTO> createJob(@RequestBody JobDTO jobDTO){
        JobDTO newJob = jobService.createJob(jobDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newJob);
    }

    @GetMapping
    public ResponseEntity<List<JobDTO>> getAllJob(){
        List<JobDTO> jobs = jobService.getAllJob();
        return ResponseEntity.status(HttpStatus.OK).body(jobs);
    }

    @PutMapping("/{id}")
    public  ResponseEntity<JobDTO> updateJob(@PathVariable Long id, @RequestBody JobDTO jobDTO){
        JobDTO jobUpdated = jobService.updateJob(id, jobDTO);
        return ResponseEntity.status(HttpStatus.OK).body(jobUpdated);
    }
    @GetMapping("/{id}")
    public  ResponseEntity<JobDTO> getJobById(@PathVariable Long id){
        JobDTO job = jobService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(job);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable Long id){
        jobService.deleteJob(id);
        return ResponseEntity.status(HttpStatus.OK).body("Delete success");
    }

    @GetMapping("/search")
    public ResponseEntity<List<JobDTO>> findByName(@RequestParam String keyword){
        List<JobDTO> jobs = jobService.findByName(keyword);
        return ResponseEntity.status(HttpStatus.OK).body(jobs);
    }
}
