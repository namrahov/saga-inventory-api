package com.boot.inventoriapi.controller;


import com.boot.inventoriapi.model.OrderRequest;
import com.boot.inventoriapi.model.ServiceResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    private final Map<Long, String> reservations = new ConcurrentHashMap<>();

    @PostMapping("/reserve")
    public ServiceResult reserve(@RequestBody OrderRequest request) {
        if (request.quantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        reservations.put(request.orderId(), "RESERVED");
        return new ServiceResult("RESERVED", "Inventory reserved for order " + request.orderId());
    }

    @PostMapping("/release")
    public ServiceResult release(@RequestBody OrderRequest request) {
        reservations.put(request.orderId(), "RELEASED");
        return new ServiceResult("RELEASED", "Inventory released for order " + request.orderId());
    }

    @GetMapping("/{orderId}")
    public ServiceResult status(@PathVariable Long orderId) {
        return new ServiceResult(reservations.getOrDefault(orderId, "NOT_FOUND"), "Inventory status");
    }
}
