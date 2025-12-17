package com.example.ecommercebe.service;

import com.example.ecommercebe.model.CartItem;
import com.example.ecommercebe.model.Order;
import com.example.ecommercebe.model.OrderItem;
import com.example.ecommercebe.repository.CartItemRepository;
import com.example.ecommercebe.repository.OrderItemRepository;
import com.example.ecommercebe.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckoutService {

    private final CartItemRepository cartRepo;
    private final OrderRepository orderRepo;
    private final OrderItemRepository orderItemRepo;

    public CheckoutService(
            CartItemRepository cartRepo,
            OrderRepository orderRepo,
            OrderItemRepository orderItemRepo
    ) {
        this.cartRepo = cartRepo;
        this.orderRepo = orderRepo;
        this.orderItemRepo = orderItemRepo;
    }

    @Transactional
    public Order checkout(String userId) {

        List<CartItem> cartItems = cartRepo.findByUserId(userId);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        long total = 0;

        for (CartItem item : cartItems) {
            total += item.getProduct().getPrice() * item.getQuantity();
        }

        Order order = new Order();
        order.setUserId(userId);
        order.setTotalAmount(total);
        order.setStatus("PENDING");

        orderRepo.save(order);

        for (CartItem item : cartItems) {
            OrderItem oi = new OrderItem();
            oi.setOrder(order);
            oi.setProduct(item.getProduct());
            oi.setQuantity(item.getQuantity());
            oi.setPrice(item.getProduct().getPrice());
            orderItemRepo.save(oi);
        }

        // clear cart
        cartRepo.deleteByUserId(userId);

        return order;
    }
}

