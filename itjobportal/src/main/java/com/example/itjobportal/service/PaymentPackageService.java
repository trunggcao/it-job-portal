package com.example.itjobportal.service;

import com.example.itjobportal.entity.PaymentPackage;
import com.example.itjobportal.repository.PaymentPackageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentPackageService {
    private final PaymentPackageRepository packageRepository;

    public PaymentPackage createPackage(PaymentPackage paymentPackage){
        return packageRepository.save(paymentPackage);
    }

    public List<PaymentPackage> getAllPackages(){
        return packageRepository.findAll();
    }

    public PaymentPackage findPackageById(Long id){
        return packageRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("No found with id" + id));
    }

    public PaymentPackage updateActiveStatus(Long id, Boolean isActive) {
        PaymentPackage paymentPackage = findPackageById(id);
        paymentPackage.setIsActive(isActive);
        return packageRepository.save(paymentPackage);
    }
}
