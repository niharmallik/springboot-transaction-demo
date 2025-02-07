package com.example.transactiondemo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a validation rule.
 */
public record ValidationRule(
    @JsonProperty("override") Boolean overrideRule,
    @JsonProperty("force") Boolean forceRule
) {}
