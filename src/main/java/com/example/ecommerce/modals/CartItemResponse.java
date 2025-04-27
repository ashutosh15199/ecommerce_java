package com.example.ecommerce.modals;

import lombok.Data;

import java.util.List;

@Data
public class CartItemResponse {
    private Long productId;
    private String productName;
    private List<String> productImages;
    private int quantity;
    private double unitPrice;
    private double totalPrice;
}