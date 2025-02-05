package com.example.transactiondemo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ValidationRule(
    @JsonProperty("override") Boolean overrideRule,
    @JsonProperty("force") Boolean forceRule
) {}
