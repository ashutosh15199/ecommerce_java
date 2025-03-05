package com.example.ecommerce.modals;

import com.example.ecommerce.entities.Role;
import lombok.Data;

@Data
public class UpdateRoleRequest {
    private Long id;
    private Role role;
}
