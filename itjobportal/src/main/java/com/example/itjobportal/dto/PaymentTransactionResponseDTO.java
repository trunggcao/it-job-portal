package com.example.itjobportal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentTransactionResponseDTO {
    private Long id;
    private String stripeSessionId;
    private String packageName;
    private Integer pointAwarded;
    private BigDecimal amout;
    private String currency;
    private String status;
    private LocalDateTime createdAt;

    private Long userId;
    private String userFullName;
    private String userEmail;
}
