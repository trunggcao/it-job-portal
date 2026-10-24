package com.example.itjobportal.service;

import com.example.itjobportal.entity.PaymentPackage;
import com.example.itjobportal.entity.PaymentTransaction;
import com.example.itjobportal.entity.User;
import com.example.itjobportal.enums.Estatus;
import com.example.itjobportal.repository.PaymentPackageRepository;
import com.example.itjobportal.repository.PaymentTransactionRepository;
import com.example.itjobportal.repository.UserRepository;
import com.stripe.model.checkout.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentWebhookService {

    private final UserRepository userRepository;
    private final PaymentPackageRepository packageRepository;
    private final PaymentTransactionRepository transactionRepository;

    @Transactional
    public void fulfillOrderDirectly(String sessionId, Long amountTotal, String currency, Map<String, String> metadata) {
        try {
            if (transactionRepository.existsByStripeSessionId(sessionId)){
                return;
            }

            if (metadata == null || !metadata.containsKey("userId")){
                return;
            }

            Long userId = Long.valueOf(metadata.get("userId"));
            Long packageId = Long.valueOf(metadata.get("packageId"));
            Integer pointsToAdd = Integer.valueOf(metadata.get("points"));
            String packageName = metadata.get("packageName");
            BigDecimal amountPaid = new BigDecimal(amountTotal);

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User is not found: " + userId));

            PaymentPackage paymentPackage = packageRepository.findById(packageId)
                    .orElseThrow(() -> new RuntimeException("Package not found: " + packageId));

            user.setPoints(user.getPoints() + pointsToAdd);
            userRepository.save(user);

            PaymentTransaction transaction = PaymentTransaction.builder()
                    .user(user)
                    .paymentPackage(paymentPackage)
                    .stripeSessionId(sessionId)
                    .packageName(packageName)
                    .pointsAwarded(pointsToAdd)
                    .amount(amountPaid)
                    .currency(currency)
                    .status(Estatus.SUCCESS)
                    .build();

            transactionRepository.save(transaction);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

}
