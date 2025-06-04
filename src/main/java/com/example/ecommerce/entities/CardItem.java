package com.example.ecommerce.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "card_items")
public class CardItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardItemId;
    @ManyToOne
    @JoinColumn(name = "card_id",nullable = false)
    private Card card;
    @ManyToOne
    @JoinColumn(name = "product_id",nullable = false)
    private ProductEntity product;
    private int quantity;
    private BigDecimal price;
}
