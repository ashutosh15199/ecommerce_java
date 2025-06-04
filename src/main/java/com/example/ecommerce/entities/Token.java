package com.example.ecommerce.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
@Table(name = "token")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;  // Changed from Integer to Long

    @Column(name = "access_token", nullable = false, unique = true)
    private String accessToken;

    @Column(name = "refresh_token", unique = true)
    private String refreshToken;

    @Column(name = "is_logged_out", nullable = false)
    private boolean loggedOut = false;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Now `user_id` will reference `User.id` which is Long

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
