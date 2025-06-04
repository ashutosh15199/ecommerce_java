package com.example.ecommerce.services;

import com.example.ecommerce.entities.ProductCategory;
import com.example.ecommerce.entities.ProductEntity;
import com.example.ecommerce.modals.ProductRequest;
import com.example.ecommerce.modals.UpdateProdectRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ProductServices {
    public ProductEntity addProductByAdmin(ProductRequest productRequest, MultipartFile[] imageUrls) throws IOException;
    public List<ProductEntity> getProductByAdmin();
    public ProductEntity getProductById(Long productId);
    public ProductEntity updateProductDetailsByAdmin(UpdateProdectRequest updateProdectRequest, Long productId) throws IOException ;
        public ProductEntity deleteProductByAdmin(Long productId);
    public List<ProductEntity> searchProduct(String query);
    public Map<ProductCategory, List<ProductEntity>> getProductsGroupedByCategory();
}
