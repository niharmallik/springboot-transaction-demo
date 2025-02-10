package com.example.transactiondemo.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

/**
 * Validator for Currency field.
 */
@Component
public class CurrencyValidator implements Validator {

    private static final Pattern CURRENCY_PATTERN = Pattern.compile("^[A-Z]{3}$");

    @Override
    public boolean supports(Class<?> clazz) {
        return String.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        String currency = (String) target;

        if (currency == null || currency.isBlank()) {
            errors.rejectValue("currency", "WPMT0018", "currency is a required field");
        } else if (!CURRENCY_PATTERN.matcher(currency).matches()) {
            errors.rejectValue("currency", "WPMT0015", "Invalid currency");
        }
    }
}
