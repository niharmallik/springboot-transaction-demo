package com.example.transactiondemo.validator;

/**
 * Enum representing validation error codes and descriptions.
 */
public enum ErrorCode {
    INVALID_JSON("WPMT0017", "Invalid JSON payload received: Error unmarshalling request"),
    PROCESSING_CODE_LENGTH("WPMT0018", "processing_code must be a maximum of 6 characters in length"),
    EXTERNAL_ACCOUNT_ID_LENGTH("WPMT0018", "external_account_id must be a maximum of 60 characters in length"),
    EXTERNAL_ACCOUNT_ID_REQUIRED("WPMT0018", "external_account_id is a required field"),
    VALUE_OUT_OF_RANGE("WPMT0018", "value must be 0 or greater"),
    VALUE_TOO_LARGE("WPMT0018", "value must be 999,999,999,999.99 or less"),
    VALUE_REQUIRED("WPMT0018", "value is a required field"),
    TRACKING_ID_LENGTH("WPMT0018", "tracking_id must be a maximum of 43 characters in length"),
    INVALID_CURRENCY("WPMT0015", "Invalid currency"),
    AMOUNT_DECIMAL_LIMIT("WPMT0018", "The amount cannot have more than two decimal places"),
    CURRENCY_REQUIRED("WPMT0018", "currency is a required field"),
    TRACKING_ID_REQUIRED("WPMT0018", "tracking_id is a required field");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
