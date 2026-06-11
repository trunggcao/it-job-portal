package com.example.itjobportal.controller;

import com.example.itjobportal.dto.CompanyVerificationDTO;
import com.example.itjobportal.entity.Company;
import com.example.itjobportal.service.CompanyVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/verifications")
public class CompanyVerificationController {

    private final CompanyVerificationService companyVerificationService;

    @PostMapping("/employer")
    public ResponseEntity<CompanyVerificationDTO> createVerifications(@RequestBody CompanyVerificationDTO companyVerificationDTO){
        CompanyVerificationDTO companyVerification = companyVerificationService.createVerification(companyVerificationDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(companyVerification);

    }
    @GetMapping("/employer/history/{id}")
    public ResponseEntity<List<CompanyVerificationDTO>> getHistory(@PathVariable Long id){
        List<CompanyVerificationDTO> companyVerifications = companyVerificationService.getHistory(id);

        return ResponseEntity.status(HttpStatus.OK).body(companyVerifications);

    }

    @GetMapping("/admin")
    public ResponseEntity<List<CompanyVerificationDTO>> getAll(){
        List<CompanyVerificationDTO> companyVerifications = companyVerificationService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(companyVerifications);
    }

    @PutMapping("/admin/approve-verification/{id}")
    public ResponseEntity<CompanyVerificationDTO> approveVerification(@PathVariable Long id){
        CompanyVerificationDTO approveVerification = companyVerificationService.approveVerification(id);
        return ResponseEntity.status(HttpStatus.OK).body(approveVerification);
    }

    @PutMapping("/admin/reject-verification/{id}")
    public ResponseEntity<CompanyVerificationDTO> rejectVerification(@PathVariable Long id){
        CompanyVerificationDTO approveVerification = companyVerificationService.rejectVerification(id);
        return ResponseEntity.status(HttpStatus.OK).body(approveVerification);
    }


}
