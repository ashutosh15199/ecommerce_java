package com.example.ecommerce.modals;

import com.example.ecommerce.entities.OrderItemEntity;
import com.example.ecommerce.entities.ShippingAddressEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {
    private Long id;
    private String orderNumber;
    private LocalDateTime orderDate;
    private String status;
    private Double totalAmount;
    private Boolean isDeleted;
    private Long userId;
    private List<OrderItemEntity> orderItems;
    private ShippingAddressEntity shippingAddresses;
}
