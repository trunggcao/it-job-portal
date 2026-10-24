package com.example.itjobportal.controller;

import com.example.itjobportal.service.PaymentWebhookService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/webhooks")
@RequiredArgsConstructor
public class StripeWebhookController {

    @Value("${stripe.webhook.secret}")
    private String endpointSecret;

    private final PaymentWebhookService webhookService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/stripe")
    public ResponseEntity<String> handleStripeWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String sigHeader) {

        Event event;
        try {
            event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
        } catch (SignatureVerificationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Webhook error");
        }

        if (event.getType() != null && event.getType().contains("checkout.session.completed")) {
            try {
                JsonNode rootNode = objectMapper.readTree(payload);
                JsonNode dataObject = rootNode.path("data").path("object");

                String sessionId = dataObject.path("id").asText();
                Long amountTotal = dataObject.path("amount_total").asLong(0);
                String currency = dataObject.path("currency").asText("usd");

                Map<String, String> metadata = new HashMap<>();
                JsonNode metadataNode = dataObject.path("metadata");
                if (metadataNode.isObject()) {
                    metadataNode.fields().forEachRemaining(entry ->
                            metadata.put(entry.getKey(), entry.getValue().asText())
                    );
                }

                webhookService.fulfillOrderDirectly(sessionId, amountTotal, currency, metadata);

            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Parse error");
            }
        }

        return ResponseEntity.ok("Received");
    }
}