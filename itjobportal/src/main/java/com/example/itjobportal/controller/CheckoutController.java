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

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class CheckoutController {

    private final StripeService stripeService;

    @PostMapping("/checkout")
    public ResponseEntity<StripeResponse> checkout(@RequestBody StripeRequest stripeRequest,
                                                   Principal principal){
        StripeResponse stripeResponse = stripeService.checkout(stripeRequest,principal);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(stripeResponse);
    }

}
