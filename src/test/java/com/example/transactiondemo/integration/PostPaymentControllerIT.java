package com.example.transactiondemo.integration;

import com.example.transactiondemo.dto.PaymentRequest;
import com.example.transactiondemo.dto.Amount;
import com.example.transactiondemo.dto.AccountDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for PostPaymentController using latest Java features.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PostPaymentControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Tests a successful payment request.
     */
    @Test
    public void testPostPayment_Success() throws Exception {
        var request = new PaymentRequest(
            new Amount("USD", BigDecimal.valueOf(100)),
            new AccountDetails("289", "220035", null),
            new AccountDetails("289", "220037", null),
            false,
            false,
            "Payment transaction",
            "txn-12345",
            OffsetDateTime.now(),
            Map.of()
        );

        var requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/corporate/v2/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().json("{"code":"WPMT0000","message":"Payment request received successfully","trackingId":"txn-12345"}"));
    }

    /**
     * Tests a payment request missing the amount field.
     */
    @Test
    public void testPostPayment_MissingAmount() throws Exception {
        var request = new PaymentRequest(
            null,  // Missing amount
            new AccountDetails("289", "220035", null),
            new AccountDetails("289", "220037", null),
            false,
            false,
            "Missing amount field",
            "txn-12346",
            OffsetDateTime.now(),
            Map.of()
        );

        var requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/corporate/v2/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("[{"code":"WPMT0018","message":"Amount is required"}]"));
    }

    /**
     * Tests a payment request missing the tracking_id field.
     */
    @Test
    public void testPostPayment_MissingTrackingId() throws Exception {
        var request = new PaymentRequest(
            new Amount("USD", BigDecimal.valueOf(100)),
            new AccountDetails("289", "220035", null),
            new AccountDetails("289", "220037", null),
            false,
            false,
            "Missing tracking_id",
            null,  // Missing tracking_id
            OffsetDateTime.now(),
            Map.of()
        );

        var requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/corporate/v2/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("[{"code":"WPMT0018","message":"Tracking ID is required"}]"));
    }
}
