package com.example.ecommerce.modals;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
public class CardItemDTO {
    private Long cardItemId;
    private Long productId;
    private Long id;
    private String name;
    private int quantity;
    private BigDecimal price;
    private double totalPrice;
    private List<String> productImages;

    public CardItemDTO(Long cardItemId,Long id, Long productId, String name, int quantity, BigDecimal price, double totalPrice, List<String> productImages) {
        this.cardItemId = cardItemId;
        this.productId = productId;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.totalPrice = totalPrice;
        this.productImages = productImages;
        this.id = id;
    }
}
