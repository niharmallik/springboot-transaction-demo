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

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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
                .andExpect(content().json("{\"tracking_id\":\"txn-12345\",\"event_datetime\":\".*\"}"));
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
                .andExpect(content().json("[{\"code\":\"WPMT0018\",\"message\":\"tracking_id must be a maximum of 43 characters in length\"}]"));
    }
}
