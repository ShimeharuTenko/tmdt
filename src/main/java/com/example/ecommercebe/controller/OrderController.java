package com.example.ecommercebe.controller;

import com.example.ecommercebe.model.Order;
import com.example.ecommercebe.service.OrderService;
import com.example.ecommercebe.security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final JwtUtil jwtUtil;

    public OrderController(OrderService orderService, JwtUtil jwtUtil) {
        this.orderService = orderService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrder(
            @PathVariable String orderId,
            @RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.substring(7);
        String userId = jwtUtil.getUserIdFromToken(token);

        return ResponseEntity.ok(
                orderService.getOrderByIdAndUser(orderId, userId)
        );
    }

    @GetMapping
    public ResponseEntity<List<Order>> getMyOrders(
            @RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.substring(7);
        String userId = jwtUtil.getUserIdFromToken(token);

        return ResponseEntity.ok(orderService.getOrdersByUser(userId));
    }
}