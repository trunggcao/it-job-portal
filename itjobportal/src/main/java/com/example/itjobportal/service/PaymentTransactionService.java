package com.example.itjobportal.service;

import com.example.itjobportal.dto.PaymentTransactionResponseDTO;
import com.example.itjobportal.entity.PaymentTransaction;
import com.example.itjobportal.repository.PaymentTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentTransactionService {
    private final PaymentTransactionRepository paymentTransactionRepository;

    private PaymentTransactionResponseDTO toDTO(PaymentTransaction paymentTransaction){
        return PaymentTransactionResponseDTO.builder()
                .id(paymentTransaction.getId())
                .stripeSessionId(paymentTransaction.getStripeSessionId())
                .packageName(paymentTransaction.getPackageName())
                .pointAwarded(paymentTransaction.getPointsAwarded())
                .amout(paymentTransaction.getAmount())
                .currency(paymentTransaction.getCurrency())
                .status(paymentTransaction.getStatus().toString())
                .createdAt(paymentTransaction.getCreatedAt())
                .userId(paymentTransaction.getUser().getId())
                .userFullName(paymentTransaction.getUser().getFullName())
                .userEmail(paymentTransaction.getUser().getEmail())
                .build();
    }

    public List<PaymentTransactionResponseDTO> getAllTransaction(){
        List<PaymentTransaction> paymentTransactions = paymentTransactionRepository.findAll();
        return paymentTransactions.stream()
                .map(this::toDTO)
                .toList();
    }
}
