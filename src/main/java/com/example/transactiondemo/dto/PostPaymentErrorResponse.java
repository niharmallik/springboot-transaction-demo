package com.example.transactiondemo.dto;

/**
 * Represents a structured validation error response.
 */
public record PostPaymentErrorResponse(String code, String message) {}
