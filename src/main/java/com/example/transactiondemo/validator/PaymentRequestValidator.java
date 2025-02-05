package com.example.transactiondemo.validator;

import com.example.transactiondemo.dto.PaymentRequest;
import org.springframework.stereotype.Component;

@Component
public class PaymentRequestValidator {

    public String validate(PaymentRequest request) {
        if (request.getAmount() == null) {
            return "Missing 'amount' in request.";
        }
        if (request.getTrackingId() == null) {
            return "Missing 'tracking_id' in request.";
        }
        boolean hasCredit = (request.getCredit() != null);
        boolean hasDebit = (request.getDebit() != null);
        if (!hasCredit && !hasDebit) {
            return "Invalid payment request: must have either credit or debit or both.";
        }
        return null;
    }
}
