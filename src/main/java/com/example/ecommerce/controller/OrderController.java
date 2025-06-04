package com.example.ecommerce.controller;

import com.example.ecommerce.modals.CheckoutSummaryResponse;
import com.example.ecommerce.modals.OrderRequest;
import com.example.ecommerce.modals.OrderResponse;
import com.example.ecommerce.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderService orderService;
    @PostMapping("/create_order")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest request){
        OrderResponse response = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }
    @GetMapping("/get_order/{id}")
    public ResponseEntity<CheckoutSummaryResponse> createOrder(@PathVariable Long id){
        CheckoutSummaryResponse response = orderService.getCheckoutSummary(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @PutMapping("/delete_order/{orderId}")
    public ResponseEntity<OrderResponse> deleteOrder(@PathVariable Long orderId){
        OrderResponse orderResponse=orderService.deleteOrder(orderId);
        return ResponseEntity.ok(orderResponse);
    }
}
