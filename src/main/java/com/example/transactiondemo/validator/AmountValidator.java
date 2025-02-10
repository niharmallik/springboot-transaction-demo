package com.example.transactiondemo.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;

/**
 * Validator for Amount field.
 */
@Component
public class AmountValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return BigDecimal.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        BigDecimal value = (BigDecimal) target;

        if (value == null) {
            errors.rejectValue("value", "WPMT0018", "value is a required field");
        } else if (value.compareTo(BigDecimal.ZERO) < 0) {
            errors.rejectValue("value", "WPMT0018", "value must be 0 or greater");
        } else if (value.compareTo(new BigDecimal("999999999999.99")) > 0) {
            errors.rejectValue("value", "WPMT0018", "value must be 999,999,999,999.99 or less");
        } else if (value.scale() > 2) {
            errors.rejectValue("value", "WPMT0018", "The amount cannot have more than two decimal places");
        }
    }
}
