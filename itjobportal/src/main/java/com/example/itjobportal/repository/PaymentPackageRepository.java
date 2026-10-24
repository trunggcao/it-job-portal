package com.example.itjobportal.repository;

import com.example.itjobportal.entity.PaymentPackage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentPackageRepository extends JpaRepository<PaymentPackage, Long> {
    List<PaymentPackage> findByIsActiveTrue();
}
