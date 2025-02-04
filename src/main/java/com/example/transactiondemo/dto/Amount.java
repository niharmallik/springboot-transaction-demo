package com.example.transactiondemo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Amount {

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("value")
    private BigDecimal value;
}
