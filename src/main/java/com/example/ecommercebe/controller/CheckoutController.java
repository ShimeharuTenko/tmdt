package com.example.ecommercebe.controller;

import com.example.ecommercebe.model.Order;
import com.example.ecommercebe.security.JwtUtil;
import com.example.ecommercebe.service.CheckoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.attribute.UserPrincipal;
import java.util.Map;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    private final CheckoutService checkoutService;
    private final JwtUtil jwtUtil;

    public CheckoutController(CheckoutService checkoutService, JwtUtil jwtUtil) {
        this.checkoutService = checkoutService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public ResponseEntity<?> checkout(
            @RequestHeader("Authorization") String authHeader
    ) {
        String userId = getUserId(authHeader);

        Order order = checkoutService.checkout(userId);

        return ResponseEntity.ok(Map.of(
                "id", order.getId(),
                "message", "Checkout successful"
        ));
    }

    private String getUserId(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }
        String token = authHeader.substring(7);
        return jwtUtil.getUserIdFromToken(token);
    }
}

