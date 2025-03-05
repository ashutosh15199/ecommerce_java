package com.example.ecommerce.modals;

import com.example.ecommerce.entities.ProductCategory;
import lombok.Data;

@Data
public class UpdateProdectRequest {
    private Long productId;
    private String name;
    private String description;
    private Double price;
    private Integer stockQuantity;
    private String imageURL;
    private ProductCategory productCategory;
}
