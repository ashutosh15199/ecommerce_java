package com.example.ecommerce.repository;

import com.example.ecommerce.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {  // Changed Integer to Long

    @Query("""
        select t from Token t inner join User u on t.user.id = u.id
        where t.user.id = :userId and t.loggedOut = false
    """)
    List<Token> findAllAccessTokensByUser(Long userId);  // Changed parameter type to Long

    Optional<Token> findByAccessToken(String token);

    Optional<Token> findByRefreshToken(String token);
}
