package com.example.transactiondemo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import java.util.Map;

public record PaymentRequest(
    @JsonProperty("amount") Amount amount,
    @JsonProperty("credit") AccountDetails credit,
    @JsonProperty("debit") AccountDetails debit,
    @JsonProperty("skip_account_date_validation") Boolean skipAccountDateValidation,
    @JsonProperty("force_post") Boolean forcePost,
    @JsonProperty("soft_descriptor") String softDescriptor,
    @JsonProperty("tracking_id") String trackingId,
    @JsonProperty("schedule_datetime") OffsetDateTime scheduleDateTime,
    @JsonProperty("validation_rules") Map<String, ValidationRule> validationRules
) {}
