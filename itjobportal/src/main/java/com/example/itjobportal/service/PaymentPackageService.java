package com.example.itjobportal.service;

import com.example.itjobportal.entity.PaymentPackage;
import com.example.itjobportal.repository.PaymentPackageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentPackageService {
    private final PaymentPackageRepository packageRepository;

    public PaymentPackage createPackage(PaymentPackage paymentPackage){
        return packageRepository.save(paymentPackage);
    }
}
