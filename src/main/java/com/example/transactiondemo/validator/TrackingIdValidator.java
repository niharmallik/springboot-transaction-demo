package com.example.transactiondemo.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validator for tracking_id field.
 */
@Component
public class TrackingIdValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return String.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        String trackingId = (String) target;

        if (trackingId == null || trackingId.isBlank()) {
            errors.rejectValue("tracking_id", "WPMT0018", "tracking_id is a required field");
        } else if (trackingId.length() > 43) {
            errors.rejectValue("tracking_id", "WPMT0018", "tracking_id must be a maximum of 43 characters in length");
        }
    }
}
