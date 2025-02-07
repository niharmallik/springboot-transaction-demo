package com.example.transactiondemo.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validator for external_account_id field.
 */
@Component
public class ExternalAccountIdValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return String.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        String externalAccountId = (String) target;

        if (externalAccountId == null || externalAccountId.isBlank()) {
            errors.rejectValue("external_account_id", "WPMT0018", "external_account_id is a required field");
        } else if (externalAccountId.length() > 60) {
            errors.rejectValue("external_account_id", "WPMT0018", "external_account_id must be a maximum of 60 characters in length");
        }
    }
}
