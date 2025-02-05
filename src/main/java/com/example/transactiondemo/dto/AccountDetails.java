package com.example.transactiondemo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AccountDetails(
    @JsonProperty("external_account_id") String externalAccountId,
    @JsonProperty("processing_code") String processingCode,
    @JsonProperty("earmark_id") String earmarkId
) {}
