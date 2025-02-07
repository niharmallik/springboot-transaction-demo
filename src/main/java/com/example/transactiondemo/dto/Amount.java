package com.example.transactiondemo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * Represents a transaction amount.
 */
public record Amount(
    @NotNull(message = "Currency is required")
    @Size(min = 3, max = 3, message = "Currency must be a 3-letter ISO code")
    @JsonProperty("currency") String currency,

    @NotNull(message = "Value is required")
    @Positive(message = "Value must be greater than zero")
    @JsonProperty("value") BigDecimal value
) {}
