package com.example.ecommerce.modals;

import com.example.ecommerce.entities.ProductCategory;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdateProdectRequest {
    private Long productId;
    private String name;
    private String description;
    private Double price;
    private Integer stockQuantity;
    private MultipartFile[] imageUrls;
    private ProductCategory productCategory;
}
