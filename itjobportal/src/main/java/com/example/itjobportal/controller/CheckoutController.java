package com.example.itjobportal.controller;

import com.example.itjobportal.dto.StripeRequest;
import com.example.itjobportal.dto.StripeResponse;
import com.example.itjobportal.entity.PaymentPackage;
import com.example.itjobportal.repository.PaymentPackageRepository;
import com.example.itjobportal.service.StripeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CheckoutController {

    private final StripeService stripeService;
    private final PaymentPackageRepository packageRepository;

    @GetMapping("/packages")
    public ResponseEntity<List<PaymentPackage>> getActivePackages(){
        List<PaymentPackage> paymentPackage = packageRepository.findByIsActiveTrue();
        return ResponseEntity.status(HttpStatus.OK).body(paymentPackage);
    }

    @PostMapping("/checkout")
    public ResponseEntity<StripeResponse> checkout(@RequestBody StripeRequest stripeRequest,
                                                   Principal principal){
        StripeResponse stripeResponse = stripeService.checkout(stripeRequest,principal);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(stripeResponse);
    }

}
