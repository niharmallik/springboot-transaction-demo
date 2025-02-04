package com.example.transactiondemo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AccountDetails {

    @JsonProperty("external_account_id")
    private String externalAccountId;

    @JsonProperty("processing_code")
    private String processingCode;

    @JsonProperty("earmark_id")
    private String earmarkId;
}
