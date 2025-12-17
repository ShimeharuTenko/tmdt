package com.example.ecommercebe.controller;

import com.example.ecommercebe.model.Order;
import com.example.ecommercebe.security.JwtUtil;
import com.example.ecommercebe.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {

    private final OrderService orderService;
    private final JwtUtil jwtUtil;

    public AdminOrderController(OrderService orderService, JwtUtil jwtUtil) {
        this.orderService = orderService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders(
            @RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.substring(7);

        String role = jwtUtil.getRoleFromToken(token);

        if (!"ADMIN".equals(role)) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .build();
        }

        List<Order> orders = orderService.getAllOrdersForAdmin();
        return ResponseEntity.ok(orders);
    }
}

