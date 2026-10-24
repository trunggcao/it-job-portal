package com.example.itjobportal.repository;

import com.example.itjobportal.entity.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction , Long> {
    boolean existsByStripeSessionId(String stripeSessionId);
    List<PaymentTransaction> findByUserId(Long userId);
}
