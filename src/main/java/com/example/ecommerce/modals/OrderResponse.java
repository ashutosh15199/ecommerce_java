package com.example.ecommerce.modals;

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
public class OrderResponse {
    private Long orderId;
    private Long userId;
    private String orderNumber;
    private LocalDateTime orderDate;
    private String status;
    private Boolean isDeleted;
    private SippingAddressDTO shippingAddresses;
    private List<OrderItemDTO> orderItems;
    private Double totalAmount;
    private String message;
}
