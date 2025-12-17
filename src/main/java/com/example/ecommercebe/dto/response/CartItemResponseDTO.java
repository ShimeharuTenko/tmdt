package com.example.ecommercebe.dto.response;

import java.math.BigDecimal;

public class CartItemResponseDTO {
    private String cartItemId;
    private String productId;
    private String productName;
    private String productImage;
    private Double price;
    private Integer quantity;
    private Double totalPrice;

    // constructor
    public CartItemResponseDTO(
            String cartItemId,
            String productId,
            String productName,
            String productImage,
            Double price,
            Integer quantity
    ) {
        this.cartItemId = cartItemId;
        this.productId = productId;
        this.productName = productName;
        this.productImage = productImage;
        this.price = price;
        this.quantity = quantity;
        this.totalPrice = price * quantity;
    }

    // getters

    public String getCartItemId() {
        return cartItemId;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }
}

