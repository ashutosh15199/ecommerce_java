package com.example.ecommerce.services;

import com.example.ecommerce.entities.CardItem;

import java.util.List;

public interface CardItemService {
    public List<CardItem> getCartItemsByUserId(Long id);
}
