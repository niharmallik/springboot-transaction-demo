package com.example.transactiondemo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ValidationRule {

    @JsonProperty("override")
    private Boolean overrideRule;

    @JsonProperty("force")
    private Boolean forceRule;
}
