package com.example.itjobportal.service;

import com.example.itjobportal.repository.CompanyVerificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyVerificationService {

    private final CompanyVerificationRepository companyVerificationRepository;
}
