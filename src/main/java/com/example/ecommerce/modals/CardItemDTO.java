package com.example.ecommerce.modals;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CardItemDTO {
    private Long cardItemId;
    private String name;
    private int quantity;
    private double price;
    private double totalPrice;
    private List<String> productImages;

    public CardItemDTO(Long cardItemId, String name, int quantity, double price, double totalPrice, List<String> productImages) {
        this.cardItemId = cardItemId;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.totalPrice = totalPrice;
        this.productImages = productImages;
    }
}
