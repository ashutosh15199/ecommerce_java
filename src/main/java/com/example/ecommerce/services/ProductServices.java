package com.example.ecommerce.services;

import com.example.ecommerce.entities.ProductEntity;
import com.example.ecommerce.entities.User;
import com.example.ecommerce.modals.ProductRequest;
import com.example.ecommerce.modals.UpdateProdectRequest;

import java.util.List;

public interface ProductServices {
    public ProductEntity addProductByAdmin(ProductRequest productRequest);
    public List<ProductEntity> getProductByAdmin();
    public ProductEntity getProductById(Long productId);
    public ProductEntity updateProductDetailsByAdmin(UpdateProdectRequest updateProdectRequest, Long productId);
    public ProductEntity deleteProductByAdmin(Long productId);
}
