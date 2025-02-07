package com.example.transactiondemo.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validator for processing_code field.
 */
@Component
public class ProcessingCodeValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return String.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        String processingCode = (String) target;

        if (processingCode != null && processingCode.length() > 6) {
            errors.rejectValue("processing_code", "WPMT0018", "processing_code must be a maximum of 6 characters in length");
        }
    }
}
