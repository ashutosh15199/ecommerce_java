package com.example.ecommerce.repository;

import com.example.ecommerce.entities.ProductCategory;
import com.example.ecommerce.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.Optional;

public interface ProductRepo extends JpaRepository<ProductEntity,Long> {
    Optional<ProductEntity> findByName(String name);
    List<ProductEntity> findByProductCategory(ProductCategory productCategory);
    ProductEntity findByProductId(Long productId);
    List<ProductEntity> findByNameContainingIgnoreCase(String query);
    Optional<ProductEntity>findById(Long productId);
}
