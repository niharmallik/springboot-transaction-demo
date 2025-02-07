package com.example.transactiondemo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.Map;

/**
 * Represents a payment request.
 */
public record PaymentRequest(
    @NotNull(message = "Amount is required") @JsonProperty("amount") Amount amount,
    @JsonProperty("credit") AccountDetails credit,
    @JsonProperty("debit") AccountDetails debit,
    @JsonProperty("skip_account_date_validation") Boolean skipAccountDateValidation,
    @JsonProperty("force_post") Boolean forcePost,
    @JsonProperty("soft_descriptor") String softDescriptor,
    @NotNull(message = "Tracking ID is required") @JsonProperty("tracking_id") String trackingId,
    @JsonProperty("schedule_datetime") OffsetDateTime scheduleDateTime,
    @JsonProperty("validation_rules") Map<String, ValidationRule> validationRules
) {}
