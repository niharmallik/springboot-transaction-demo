package com.example.transactiondemo.dto;

/**
 * Represents a structured success response.
 */
public record SuccessResponse(String code, String message, String trackingId) {}
