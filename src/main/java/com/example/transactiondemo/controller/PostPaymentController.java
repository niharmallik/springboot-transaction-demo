package com.example.transactiondemo.controller;

import com.example.transactiondemo.dto.PaymentRequest;
import com.example.transactiondemo.dto.PostPaymentErrorResponse;
import com.example.transactiondemo.dto.PostPaymentResponse;
import com.example.transactiondemo.validator.PostPaymentValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles payment requests at the /corporate/v2/payments endpoint.
 */
@RestController
@RequestMapping("/corporate/v2/payments")
public class PostPaymentController {

    private final PostPaymentValidator validator;

    public PostPaymentController(PostPaymentValidator validator) {
        this.validator = validator;
    }

    @PostMapping
    public ResponseEntity<?> postPayment(@Valid @RequestBody PaymentRequest request) {
        Errors errors = new BeanPropertyBindingResult(request, "paymentRequest");
        validator.validate(request, errors);

        if (errors.hasErrors()) {
            List<PostPaymentErrorResponse> errorResponses = errors.getAllErrors().stream()
                    .map(error -> new PostPaymentErrorResponse(error.getCode(), error.getDefaultMessage()))
                    .collect(Collectors.toList());

            return ResponseEntity.badRequest().body(errorResponses);
        }

        return ResponseEntity.ok(new PostPaymentResponse(request.trackingId(), OffsetDateTime.now()));
    }
}
