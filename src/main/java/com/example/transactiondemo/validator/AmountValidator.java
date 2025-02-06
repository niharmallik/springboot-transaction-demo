package com.example.transactiondemo.validator;

import com.example.transactiondemo.dto.Amount;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validator for the Amount DTO.
 */
@Component
public class AmountValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Amount.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Amount amount = (Amount) target;

        if (amount.currency() == null || amount.currency().isBlank()) {
            errors.rejectValue("currency", "currency.empty", "Currency is required");
        }

        if (amount.value() == null || amount.value().signum() <= 0) {
            errors.rejectValue("value", "value.invalid", "Value must be greater than zero");
        }
    }
}
