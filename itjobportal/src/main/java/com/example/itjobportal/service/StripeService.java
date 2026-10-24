package com.example.itjobportal.service;

import com.example.itjobportal.dto.StripeRequest;
import com.example.itjobportal.dto.StripeResponse;
import com.example.itjobportal.entity.PaymentPackage;
import com.example.itjobportal.entity.User;
import com.example.itjobportal.repository.PaymentPackageRepository;
import com.example.itjobportal.repository.UserRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.security.Principal;

@Service
@RequiredArgsConstructor
public class StripeService {

    @Value("${stripe.secretKey}")
    private String secretKey;

    private final PaymentPackageRepository packageRepository;
    private final UserRepository userRepository;


    public StripeResponse checkout(StripeRequest stripeRequest, Principal principal){
        Stripe.apiKey= secretKey;

        //find user
        String email = principal.getName();
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        // payment package
        PaymentPackage selectedPackage = packageRepository.findById(stripeRequest.getPackageId())
                .orElseThrow(() -> new RuntimeException("PaymentPackage is not exist"));

        if (Boolean.FALSE.equals(selectedPackage.getIsActive())) {
            throw new RuntimeException("this PaymentPackage is not available");
        }

        SessionCreateParams.LineItem.PriceData.ProductData productData = SessionCreateParams.LineItem.PriceData.ProductData.builder()
                .setName(selectedPackage.getName()).build();

        SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency(stripeRequest.getCurrency() == null ? "vnd" : stripeRequest.getCurrency())
                .setUnitAmount(selectedPackage.getPrice().longValue())
                .setProductData(productData)
                .build();

        SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                .setQuantity(stripeRequest.getQuantity() != null ? stripeRequest.getQuantity() : 1L)
                .setPriceData(priceData)
                .build();

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:5454/success")
                .setCancelUrl("http://localhost:5454/cancel")
                .addLineItem(lineItem)

                .putMetadata("userId", String.valueOf(currentUser.getId()))
                .putMetadata("packageId", String.valueOf(selectedPackage.getId()))
                .putMetadata("points", String.valueOf(selectedPackage.getPoints()))
                .putMetadata("packageName", selectedPackage.getName())
                .build();

        Session session=null;

        try {
            session = Session.create(params);
        } catch (StripeException ex){
            return StripeResponse.builder()
                    .status("FAILED")
                    .message(ex.getMessage())
                    .build();
        }

        return StripeResponse.builder()
                .status("SUCCESS")
                .message("Payment session create")
                .sessionId(session.getId())
                .sessionUrl(session.getUrl())
                .build();


    }

}
