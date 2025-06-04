package com.example.ecommerce.modals;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardDTO {
    private Long cardId;
    private List<CardItemDTO> cartItems;
    private double totalCartPrice;

    public CardDTO(Long cardId,List<CardItemDTO> cartItems) {
        this.cartItems = cartItems;
        this.cardId=cardId;
        this.totalCartPrice = cartItems.stream()
                .mapToDouble(CardItemDTO::getTotalPrice)
                .sum();
    }


}
