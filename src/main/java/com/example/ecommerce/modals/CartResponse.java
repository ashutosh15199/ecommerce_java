package com.example.ecommerce.modals;

import lombok.Data;

import java.util.List;

@Data
public class CartResponse {
    private Long cardId;
    private Long userId;
    private List<CartItemResponse> items;
}