package com.example.transactiondemo.controller;

import com.example.transactiondemo.dto.PaymentRequest;
import com.example.transactiondemo.validator.PaymentRequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/corporate/v2/payments")
public class PostPaymentController {

    @Autowired
    private PaymentRequestValidator validator;

    @PostMapping
    public ResponseEntity<String> postPayment(@RequestBody PaymentRequest request) {
        String validationError = validator.validate(request);
        if (validationError != null) {
            return ResponseEntity.badRequest().body(validationError);
        }
        return ResponseEntity.ok("Payment request received with tracking ID: " + request.getTrackingId());
    }
}
