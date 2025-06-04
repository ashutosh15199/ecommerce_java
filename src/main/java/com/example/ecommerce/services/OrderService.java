package com.example.ecommerce.services;

import com.example.ecommerce.modals.CheckoutSummaryResponse;
import com.example.ecommerce.modals.OrderRequest;
import com.example.ecommerce.modals.OrderResponse;

public interface OrderService{
    public OrderResponse createOrder(OrderRequest orderRequest);
    public CheckoutSummaryResponse getCheckoutSummary(Long id);
    public OrderResponse deleteOrder(Long orderId);
}
