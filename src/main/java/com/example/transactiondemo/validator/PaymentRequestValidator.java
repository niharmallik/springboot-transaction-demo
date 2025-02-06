package com.example.transactiondemo.validator;

import com.example.transactiondemo.dto.PaymentRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validator for the PaymentRequest DTO.
 */
@Component
public class PaymentRequestValidator implements Validator {

    private final AmountValidator amountValidator;
    private final AccountDetailsValidator accountDetailsValidator;

    public PaymentRequestValidator(AmountValidator amountValidator, AccountDetailsValidator accountDetailsValidator) {
        this.amountValidator = amountValidator;
        this.accountDetailsValidator = accountDetailsValidator;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return PaymentRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PaymentRequest request = (PaymentRequest) target;

        if (request.amount() == null) {
            errors.rejectValue("amount", "amount.null", "Amount is required");
        } else {
            errors.pushNestedPath("amount");
            amountValidator.validate(request.amount(), errors);
            errors.popNestedPath();
        }

        if (request.trackingId() == null || request.trackingId().isBlank()) {
            errors.rejectValue("trackingId", "trackingId.empty", "Tracking ID is required");
        }

        boolean hasCredit = request.credit() != null;
        boolean hasDebit = request.debit() != null;

        if (!hasCredit && !hasDebit) {
            errors.reject("invalidTransaction", "Transaction must have either credit or debit or both.");
        }

        if (hasCredit) {
            errors.pushNestedPath("credit");
            accountDetailsValidator.validate(request.credit(), errors);
            errors.popNestedPath();
        }

        if (hasDebit) {
            errors.pushNestedPath("debit");
            accountDetailsValidator.validate(request.debit(), errors);
            errors.popNestedPath();
        }
    }
}
