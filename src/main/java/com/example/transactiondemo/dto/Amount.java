package com.example.transactiondemo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public record Amount(
    @JsonProperty("currency") String currency,
    @JsonProperty("value") BigDecimal value
) {}
