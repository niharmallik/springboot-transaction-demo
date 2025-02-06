package com.example.transactiondemo.validator;

import com.example.transactiondemo.dto.AccountDetails;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validator for the AccountDetails DTO.
 */
@Component
public class AccountDetailsValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return AccountDetails.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AccountDetails accountDetails = (AccountDetails) target;

        if (accountDetails.externalAccountId() == null || accountDetails.externalAccountId().isBlank()) {
            errors.rejectValue("externalAccountId", "externalAccountId.empty", "External account ID is required");
        }

        if (accountDetails.processingCode() == null || accountDetails.processingCode().isBlank()) {
            errors.rejectValue("processingCode", "processingCode.empty", "Processing code is required");
        }
    }
}
