package com.example.itjobportal.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StripeRequest {
    private Long packageId;
    private Long quantity;
    private String currency;
}
