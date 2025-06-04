package com.example.ecommerce.modals;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
@Data
@Builder
public class CheckoutSummaryResponse {
    private List<CardItemDTO> orderItems;
    private SippingAddressDTO shippingAddresses;
    private BigDecimal subtotal;
    private BigDecimal discount;
    private BigDecimal shippingCharge;
    private BigDecimal tax;
    private BigDecimal totalAmount;
    private LocalDate estimatedDeliveryDate;
}
