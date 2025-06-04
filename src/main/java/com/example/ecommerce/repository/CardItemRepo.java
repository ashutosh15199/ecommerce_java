package com.example.ecommerce.repository;

import com.example.ecommerce.entities.Card;
import com.example.ecommerce.entities.CardItem;
import com.example.ecommerce.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CardItemRepo extends JpaRepository<CardItem,Long> {
    Optional<CardItem> findByCardAndProduct(Card card, ProductEntity product);

    List<CardItem> findByCard(Card card);

}
