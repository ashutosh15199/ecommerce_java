package com.example.ecommerce.repository;

import com.example.ecommerce.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepo extends JpaRepository<ProductEntity,Long> {
    Optional<ProductEntity> findByName(String name);

    ProductEntity findByProductId(Long productId);
}
