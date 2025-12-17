package com.example.ecommercebe.dto.request;

public class CartItemRequest {
    private String productId;
    private Integer quantity;

    // getters/setters


    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}