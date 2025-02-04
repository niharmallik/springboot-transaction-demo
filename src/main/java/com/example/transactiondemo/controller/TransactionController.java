package com.example.transactiondemo.controller;

import com.example.transactiondemo.dto.TransactionRequest;
import com.example.transactiondemo.validator.TransactionRequestValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionRequestValidator validator;

    public TransactionController(TransactionRequestValidator validator) {
        this.validator = validator;
    }

    @PostMapping
    public ResponseEntity<String> processTransaction(@RequestBody TransactionRequest request) {
        String validationError = validator.validate(request);
        if (validationError != null) {
            return ResponseEntity.badRequest().body(validationError);
        }
        return ResponseEntity.ok("Transaction processed with tracking ID: " + request.getTrackingId());
    }
}
