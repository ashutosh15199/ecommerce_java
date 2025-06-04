package com.example.ecommerce.modals;

import lombok.Data;

import java.util.List;

@Data
public class AddToCardRequest {
    private List<CartItemRequest> cartItems;
    private Long id; // optional
}

