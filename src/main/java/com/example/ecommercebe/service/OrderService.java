package com.example.ecommercebe.service;

import com.example.ecommercebe.model.Order;
import com.example.ecommercebe.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepo;

    public OrderService(OrderRepository orderRepo) {
        this.orderRepo = orderRepo;
    }

    public Order getOrderByIdAndUser(String orderId, String userId) {
        return orderRepo.findByIdAndUserId(orderId, userId)
                .orElseThrow(() ->
                        new RuntimeException("Order not found")
                );
    }

    public List<Order> getOrdersByUser(String userId) {
        return orderRepo.findByUserIdOrderByCreatedAtDesc(userId);
    }


    public List<Order> getAllOrdersForAdmin() {
        return orderRepo.findAllByOrderByCreatedAtDesc();
    }
}
