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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PostPaymentControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testPostPayment_Success() throws Exception {
        PaymentRequest request = new PaymentRequest();
        request.setAmount(new Amount("USD", BigDecimal.valueOf(100)));
        request.setCredit(new AccountDetails("289", "220035", null));
        request.setDebit(new AccountDetails("289", "220037", null));
        request.setSoftDescriptor("Payment transaction");
        request.setTrackingId("12345");
        request.setScheduleDateTime(OffsetDateTime.now());

        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/corporate/v2/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Payment request received with tracking ID: 12345"));
    }

    @Test
    public void testPostPayment_MissingAmount() throws Exception {
        PaymentRequest request = new PaymentRequest();
        request.setCredit(new AccountDetails("289", "220035", null));
        request.setDebit(new AccountDetails("289", "220037", null));
        request.setSoftDescriptor("Payment transaction");
        request.setTrackingId("12345");
        request.setScheduleDateTime(OffsetDateTime.now());

        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/corporate/v2/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Missing 'amount' in request."));
    }

    @Test
    public void testPostPayment_MissingTrackingId() throws Exception {
        PaymentRequest request = new PaymentRequest();
        request.setAmount(new Amount("USD", BigDecimal.valueOf(100)));
        request.setCredit(new AccountDetails("289", "220035", null));
        request.setDebit(new AccountDetails("289", "220037", null));
        request.setSoftDescriptor("Payment transaction");
        request.setScheduleDateTime(OffsetDateTime.now());

        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/corporate/v2/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Missing 'tracking_id' in request."));
    }
}
