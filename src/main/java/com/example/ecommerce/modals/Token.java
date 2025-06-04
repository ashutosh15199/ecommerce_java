package com.example.ecommerce.modals;

import jakarta.persistence.*;
import lombok.Data;


@Data
public class Token {
    private Integer id;
    private String accessToken;
    private String refreshToken;
    private boolean loggedOut;
    private Integer userId;

}

