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
 * Integration tests for PostPaymentController.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PostPaymentControllerIT {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Autowired
    public PostPaymentControllerIT(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    /**
     * Tests a successful payment request.
     */
    @Test
    public void testPostPayment_Success() throws Exception {
        PaymentRequest request = new PaymentRequest(
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

        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/corporate/v2/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().json("{"tracking_id":"txn-12345","event_datetime":".*"}"));
    }

    /**
     * Tests a payment request with missing amount field.
     */
    @Test
    public void testPostPayment_MissingAmount() throws Exception {
        PaymentRequest request = new PaymentRequest(
            null,
            new AccountDetails("289", "220035", null),
            new AccountDetails("289", "220037", null),
            false,
            false,
            "Missing amount field",
            "txn-12346",
            OffsetDateTime.now(),
            Map.of()
        );

        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/corporate/v2/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("[{"code":"WPMT0018","message":"value is a required field"}]"));
    }

    /**
     * Tests a payment request with invalid currency format.
     */
    @Test
    public void testPostPayment_InvalidCurrency() throws Exception {
        PaymentRequest request = new PaymentRequest(
            new Amount("US", BigDecimal.valueOf(100)),  // Invalid currency format
            new AccountDetails("289", "220035", null),
            new AccountDetails("289", "220037", null),
            false,
            false,
            "Invalid currency",
            "txn-12347",
            OffsetDateTime.now(),
            Map.of()
        );

        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/corporate/v2/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("[{"code":"WPMT0015","message":"Invalid currency"}]"));
    }

    /**
     * Tests a payment request with excessive decimal places in amount value.
     */
    @Test
    public void testPostPayment_TooManyDecimals() throws Exception {
        PaymentRequest request = new PaymentRequest(
            new Amount("USD", new BigDecimal("100.123")),  // More than 2 decimal places
            new AccountDetails("289", "220035", null),
            new AccountDetails("289", "220037", null),
            false,
            false,
            "Too many decimals",
            "txn-12348",
            OffsetDateTime.now(),
            Map.of()
        );

        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/corporate/v2/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("[{"code":"WPMT0018","message":"The amount cannot have more than two decimal places"}]"));
    }

    /**
     * Tests a payment request with tracking ID exceeding 43 characters.
     */
    @Test
    public void testPostPayment_TrackingIdTooLong() throws Exception {
        PaymentRequest request = new PaymentRequest(
            new Amount("USD", BigDecimal.valueOf(100)),
            new AccountDetails("289", "220035", null),
            new AccountDetails("289", "220037", null),
            false,
            false,
            "Tracking ID too long",
            "txn-12345678901234567890123456789012345678901234",  // 44 characters
            OffsetDateTime.now(),
            Map.of()
        );

        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/corporate/v2/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("[{\"code\":\"WPMT0018\",\"message\":\"tracking_id must be a maximum of 43 characters in length\"}]\"));
    }

    /**
     * Tests a payment request with missing tracking ID.
     */
    @Test
    public void testPostPayment_MissingTrackingId() throws Exception {
        PaymentRequest request = new PaymentRequest(
            new Amount("USD", BigDecimal.valueOf(100)),
            new AccountDetails("289", "220035", null),
            new AccountDetails("289", "220037", null),
            false,
            false,
            "Missing tracking ID",
            null,
            OffsetDateTime.now(),
            Map.of()
        );

        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/corporate/v2/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("[{"code":"WPMT0018","message":"tracking_id is a required field"}]"));
    }
}
