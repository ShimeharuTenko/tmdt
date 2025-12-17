package com.example.ecommercebe.service;

import com.example.ecommercebe.dto.request.CartItemRequest;
import com.example.ecommercebe.dto.response.CartItemResponse;
import com.example.ecommercebe.dto.response.CartItemResponseDTO;
import com.example.ecommercebe.model.CartItem;
import com.example.ecommercebe.model.Product;
import com.example.ecommercebe.repository.CartItemRepository;
import com.example.ecommercebe.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    public void addToCart(
            String userId,
            String productId,
            Integer quantity
    ) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem cartItem = cartItemRepository
                .findByUserIdAndProduct_Id(userId, productId)
                .orElse(null);

        if (cartItem == null) {
            CartItem newItem = new CartItem();
            newItem.setUserId(userId);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            cartItemRepository.save(newItem);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItem.setUpdatedAt(LocalDateTime.now());
            cartItemRepository.save(cartItem);
        }
    }


    // GET CART
    public List<CartItemResponseDTO> getCart(String userId) {
        return cartItemRepository.findAllByUserId(userId)
                .stream()
                .map(item -> new CartItemResponseDTO(
                        item.getId(),
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getProduct().getThumbnail(),
                        item.getProduct().getPrice(),
                        item.getQuantity()
                ))
                .toList();
    }

    // UPDATE QUANTITY
    public void updateQuantity(String userId, String cartItemId, Integer quantity) {
        CartItem item = cartItemRepository
                .findByIdAndUserId(cartItemId, userId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        item.setQuantity(quantity);
        item.setUpdatedAt(LocalDateTime.now());
        cartItemRepository.save(item);
    }

    // DELETE ITEM
    public void deleteItem(String userId, String cartItemId) {
        CartItem item = cartItemRepository
                .findByIdAndUserId(cartItemId, userId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        cartItemRepository.delete(item);
    }
}

