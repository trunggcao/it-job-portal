package com.example.itjobportal.controller;

import com.example.itjobportal.dto.PaymentTransactionResponseDTO;
import com.example.itjobportal.dto.UpdateActiveRequest;
import com.example.itjobportal.entity.PaymentPackage;
import com.example.itjobportal.entity.PaymentTransaction;
import com.example.itjobportal.service.PaymentPackageService;
import com.example.itjobportal.service.PaymentTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminPaymentController {
    private final PaymentTransactionService transactionService;
    private final PaymentPackageService paymentPackageService;

    @GetMapping("/transactions")
    public ResponseEntity<List<PaymentTransactionResponseDTO>> getAllTransaction(){
        List<PaymentTransactionResponseDTO> transactions = transactionService.getAllTransaction();
        return ResponseEntity.status(HttpStatus.OK).body(transactions);
    }
    @PostMapping("/packages")
    public ResponseEntity<PaymentPackage> createPaymentPackage(@RequestBody PaymentPackage paymentPackage){
        PaymentPackage newPackage =  paymentPackageService.createPackage(paymentPackage);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPackage);
    }

    @GetMapping("/packages")
    public ResponseEntity<List<PaymentPackage>> getAllPackages(){
        List<PaymentPackage> newPackage =  paymentPackageService.getAllPackages();
        return ResponseEntity.status(HttpStatus.OK).body(newPackage);
    }

    @PatchMapping("/packages/{id}")
    public ResponseEntity<PaymentPackage> updateActiveStatus(@PathVariable Long id,
                                                             @RequestBody UpdateActiveRequest request){
        PaymentPackage updatedPackage = paymentPackageService.updateActiveStatus(id, request.getIsActive());
        return ResponseEntity.status(HttpStatus.OK).body(updatedPackage);
    }
}
