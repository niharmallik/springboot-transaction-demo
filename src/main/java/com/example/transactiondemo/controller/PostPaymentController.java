package com.example.transactiondemo.controller;

import com.example.transactiondemo.dto.PaymentRequest;
import com.example.transactiondemo.validator.PaymentRequestValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Handles payment requests at the /corporate/v2/payments endpoint.
 */
@RestController
@RequestMapping("/corporate/v2/payments")
public class PostPaymentController {

    private final PaymentRequestValidator validator;

    public PostPaymentController(PaymentRequestValidator validator) {
        this.validator = validator;
    }

    @PostMapping
    public ResponseEntity<String> postPayment(@Valid @RequestBody PaymentRequest request) {
        Errors errors = new BeanPropertyBindingResult(request, "paymentRequest");
        validator.validate(request, errors);

        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getAllErrors().toString());
        }

        return ResponseEntity.ok("Payment request received with tracking ID: " + request.trackingId());
    }
}
