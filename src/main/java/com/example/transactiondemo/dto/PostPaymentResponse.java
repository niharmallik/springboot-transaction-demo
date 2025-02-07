package com.example.transactiondemo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

/**
 * Represents a structured success response.
 */
public record PostPaymentResponse(
    @JsonProperty("tracking_id") String trackingId,
    @JsonProperty("event_datetime") OffsetDateTime eventDateTime
) {}
