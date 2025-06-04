package com.example.ecommerce.repository;

import com.example.ecommerce.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepo extends JpaRepository<OrderEntity,Long> {

    Optional<OrderEntity> findByOrderId(Long orderId);
}
