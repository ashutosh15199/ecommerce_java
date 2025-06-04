package com.example.ecommerce.modals;

import lombok.Data;

@Data
public class RegisterRequest {
    private String firstname;
    private String lastname;
    private String username;
    private String password;
}
