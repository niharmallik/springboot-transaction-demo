package com.example.transactiondemo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class TransactionRequest {

    @JsonProperty("amount")
    private Amount amount;

    @JsonProperty("credit")
    private AccountDetails credit;

    @JsonProperty("debit")
    private AccountDetails debit;

    @JsonProperty("skip_account_date_validation")
    private Boolean skipAccountDateValidation;

    @JsonProperty("force_post")
    private Boolean forcePost;

    @JsonProperty("soft_descriptor")
    private String softDescriptor;

    @JsonProperty("tracking_id")
    private String trackingId;

    @JsonProperty("schedule_datetime")
    private OffsetDateTime scheduleDateTime;
}
