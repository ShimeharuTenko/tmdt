package com.example.ecommercebe.repository;

import com.example.ecommercebe.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, String> {
    List<CartItem> findByUserId(String userId);
    Optional<CartItem> findByUserIdAndProduct_Id(String userId, String productId);

    List<CartItem> findAllByUserId(String userId);

    Optional<CartItem> findByIdAndUserId(String id, String userId);
    void deleteByUserId(String userId);
}
