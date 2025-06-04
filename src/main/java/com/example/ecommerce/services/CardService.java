package com.example.ecommerce.services;

import com.example.ecommerce.entities.Card;
import com.example.ecommerce.entities.CardItem;
import com.example.ecommerce.entities.User;
import com.example.ecommerce.modals.CardDTO;
import com.example.ecommerce.modals.CartItemRequest;
import com.example.ecommerce.modals.CartResponse;

import java.util.List;

public interface CardService {
    CartResponse addToCard(List<CartItemRequest> cartItems, Long id);
    public List<CardDTO> getAllCard();
    public CardDTO getCard(Long id);
    public void deleteCard(Long productId,Long id);
//    public Card getOrCreateCartForUser(User user);
}
