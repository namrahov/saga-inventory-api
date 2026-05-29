package com.boot.inventoriapi.model;

public record OrderRequest(Long orderId, Long customerId, String productCode, int quantity, int amount) {}
