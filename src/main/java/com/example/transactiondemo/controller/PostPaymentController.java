package com.example.transactiondemo.controller;

import com.example.transactiondemo.dto.PaymentRequest;
import com.example.transactiondemo.dto.ErrorResponse;
import com.example.transactiondemo.dto.SuccessResponse;
import com.example.transactiondemo.validator.PaymentRequestValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<?> postPayment(@Valid @RequestBody PaymentRequest request) {
        Errors errors = new BeanPropertyBindingResult(request, "paymentRequest");
        validator.validate(request, errors);

        if (errors.hasErrors()) {
            List<ErrorResponse> errorResponses = errors.getAllErrors().stream()
                    .map(error -> new ErrorResponse("WPMT0018", error.getDefaultMessage()))
                    .collect(Collectors.toList());

            return ResponseEntity.badRequest().body(errorResponses);
        }

        return ResponseEntity.ok(new SuccessResponse("WPMT0000", "Payment request received successfully", request.trackingId()));
    }
}
