package com.example.ecommerce.repository;

import com.example.ecommerce.entities.ShippingAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface ShippingAddressRepo extends JpaRepository<ShippingAddressEntity,Long> {
     Optional<ShippingAddressEntity> findByUser_Id(Long id);
}
