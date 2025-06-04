package com.example.ecommerce.serviceImpls;

import com.example.ecommerce.entities.Card;
import com.example.ecommerce.entities.CardItem;
import com.example.ecommerce.repository.CardItemRepo;
import com.example.ecommerce.repository.CardRepo;
import com.example.ecommerce.services.CardItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CardItemServiceImpl implements CardItemService {
    @Autowired
    private CardRepo cardRepo;

    @Override
    public List<CardItem> getCartItemsByUserId(Long id) {
        return cardRepo.findByUser_Id(id)
                .map(Card::getCartItems)
                .orElse(Collections.emptyList());
    }


}
