package com.example.itjobportal.controller;

import com.example.itjobportal.dto.CompanyDTO;
import com.example.itjobportal.entity.Company;
import com.example.itjobportal.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    public ResponseEntity<CompanyDTO> createCompany(@RequestBody CompanyDTO companyDTO){
        CompanyDTO newCompany = companyService.createCompany(companyDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCompany);
    }

    @GetMapping
    public ResponseEntity<List<CompanyDTO>> getAll(){
        return ResponseEntity.ok(companyService.getAllCompanies());
    }

    @GetMapping("/{companyId}")
    public ResponseEntity<CompanyDTO> getCompanyById(@PathVariable Long companyId){
        CompanyDTO company = companyService.getCompanyById(companyId);
        return ResponseEntity.status(HttpStatus.OK).body(company);
    }

    @PutMapping("/{companyId}")
    public ResponseEntity<CompanyDTO> updateCompany(@PathVariable Long companyId,@RequestBody CompanyDTO companyDTO){
        CompanyDTO updateCompany = companyService.updateCompany(companyId,companyDTO);
        return ResponseEntity.ok(updateCompany);
    }

    @DeleteMapping("/{companyId}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long companyId){
        companyService.deleteCompany(companyId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<CompanyDTO>> findCompanyByNameIgnoreCase(@RequestParam String keyword){
        List<CompanyDTO> companies = companyService.findCompanyByNameIgnoreCase(keyword);
        return ResponseEntity.status(HttpStatus.OK).body(companies);
    }
}
