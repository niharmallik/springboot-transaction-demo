package com.example.transactiondemo.validator;

import com.example.transactiondemo.dto.PaymentRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

/**
 * Main validator for Post Payment Request, invoking individual field validators.
 */
@Component
public class PostPaymentValidator implements Validator {

    private final List<Validator> fieldValidators;

    public PostPaymentValidator(List<Validator> fieldValidators) {
        this.fieldValidators = fieldValidators;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return PaymentRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PaymentRequest request = (PaymentRequest) target;

        for (Validator validator : fieldValidators) {
            if (validator.supports(String.class)) {
                if (request.trackingId() != null) {
                    validator.validate(request.trackingId(), errors);
                }
                if (request.credit() != null && request.credit().processingCode() != null) {
                    validator.validate(request.credit().processingCode(), errors);
                }
                if (request.debit() != null && request.debit().processingCode() != null) {
                    validator.validate(request.debit().processingCode(), errors);
                }
                if (request.credit() != null && request.credit().externalAccountId() != null) {
                    validator.validate(request.credit().externalAccountId(), errors);
                }
                if (request.debit() != null && request.debit().externalAccountId() != null) {
                    validator.validate(request.debit().externalAccountId(), errors);
                }
                if (request.amount() != null && request.amount().currency() != null) {
                    validator.validate(request.amount().currency(), errors);
                }
            }
            if (validator.supports(Number.class)) {
                if (request.amount() != null && request.amount().value() != null) {
                    validator.validate(request.amount().value(), errors);
                }
            }
        }
    }
}
