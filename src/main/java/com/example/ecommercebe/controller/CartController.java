package com.example.ecommercebe.controller;

import com.example.ecommercebe.dto.request.AddToCartRequestDTO;
import com.example.ecommercebe.dto.request.CartItemRequest;
import com.example.ecommercebe.dto.response.CartItemResponse;
import com.example.ecommercebe.security.JwtUtil;
import com.example.ecommercebe.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody AddToCartRequestDTO request
    ) {

        String token = authHeader.substring(7);
        String userId = jwtUtil.getUserIdFromToken(token); // String UUID

        cartService.addToCart(
                userId,
                request.getProductId(),
                request.getQuantity()
        );

        return ResponseEntity.ok(
                Map.of("message", "Added to cart")
        );
    }

    private String getUserId(String authHeader) {
        String token = authHeader.substring(7);
        return jwtUtil.getUserIdFromToken(token);
    }

    // 🟢 GET CART
    @GetMapping
    public ResponseEntity<?> getCart(
            @RequestHeader("Authorization") String authHeader
    ) {
        String userId = getUserId(authHeader);
        return ResponseEntity.ok(cartService.getCart(userId));
    }

    // 🟡 UPDATE QUANTITY
    @PutMapping("/{cartItemId}")
    public ResponseEntity<?> updateQuantity(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable String cartItemId,
            @RequestBody Map<String, Integer> body
    ) {
        String userId = getUserId(authHeader);
        cartService.updateQuantity(userId, cartItemId, body.get("quantity"));
        return ResponseEntity.ok(Map.of("message", "Updated"));
    }

    // 🔴 DELETE ITEM
    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<?> deleteItem(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable String cartItemId
    ) {
        String userId = getUserId(authHeader);
        cartService.deleteItem(userId, cartItemId);
        return ResponseEntity.ok(Map.of("message", "Deleted"));
    }
}
