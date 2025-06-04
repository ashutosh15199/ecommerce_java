package com.example.ecommerce.modals;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemRequest {
    private Long productId;
    private int quantity;
}
