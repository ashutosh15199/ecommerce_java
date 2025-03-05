package com.example.ecommerce.modals;

import com.example.ecommerce.entities.Role;
import lombok.Data;

@Data
public class UserRequest {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Role role;
}