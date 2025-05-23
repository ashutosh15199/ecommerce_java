package com.example.ecommerce.modals;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class CardRequest {
    private Long userId;
    private List<CartItemRequest> cartItems;
}
