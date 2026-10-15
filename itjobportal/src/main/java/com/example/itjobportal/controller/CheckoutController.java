package com.example.itjobportal.controller;

import com.example.itjobportal.dto.StripeRequest;
import com.example.itjobportal.dto.StripeResponse;
import com.example.itjobportal.service.StripeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CheckoutController {

    private final StripeService stripeService;

    @PostMapping("/checkout")
    public ResponseEntity<StripeResponse> checkout(@RequestBody StripeRequest stripeRequest){
        StripeResponse stripeResponse = stripeService.checkout(stripeRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(stripeResponse);
    }

}
