package com.example.ecommerce.modals;

import com.example.ecommerce.entities.ProductCategory;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.beans.Transient;

@Data
public class ProductRequest {
    private String name;
    private String description;
    private Double price;
    private Integer stockQuantity;
    private MultipartFile[] imageUrls;
    private ProductCategory productCategory;
    public MultipartFile[] getImageURLs() {
        return imageUrls;
    }

    public void setImageURL(MultipartFile[] imageURLs) {
        this.imageUrls = imageURLs;
    }
}
