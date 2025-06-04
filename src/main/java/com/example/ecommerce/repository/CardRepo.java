package com.example.ecommerce.repository;

import com.example.ecommerce.entities.Card;
import com.example.ecommerce.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepo extends JpaRepository<Card,Long> {
    Card findByUser(User user);
    Card findByUserUsername(String username);
    Optional<Card> findByUserId(Long userId);  // <- custom query method
//    Card findById(Long id);
    Card findByCardId(Long cardId);

    Optional<Card> findByUser_Id(Long userId);

}
