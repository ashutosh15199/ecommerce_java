package com.example.ecommerce.serviceImpls;

import com.example.ecommerce.entities.ProductEntity;
import com.example.ecommerce.entities.Role;
import com.example.ecommerce.entities.User;
import com.example.ecommerce.modals.ProductRequest;
import com.example.ecommerce.modals.UpdateProdectRequest;
import com.example.ecommerce.repository.ProductRepo;
import com.example.ecommerce.services.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductServices{
    @Autowired
    ProductRepo productRepo;

    public ProductEntity addProductByAdmin(ProductRequest productRequest){

        Optional<ProductEntity> optionalProductEntity = productRepo.findByName(productRequest.getName());
        if(optionalProductEntity.isPresent()){
            throw new RuntimeException("Product Exist with Same Name");
        }
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(productRequest.getName());
        productEntity.setDescription(productRequest.getDescription());
        productEntity.setPrice(productRequest.getPrice());
        productEntity.setStockQuantity(productRequest.getStockQuantity());
        productEntity.setProductCategory(productRequest.getProductCategory());
        return productRepo.save(productEntity);
    }

    public List<ProductEntity> getProductByAdmin(){
        return productRepo.findAll();
    }
    public ProductEntity getProductById(Long productId){
        return productRepo.findByProductId(productId);
    }
    public ProductEntity updateProductDetailsByAdmin(UpdateProdectRequest updateProdectRequest, Long productId){
        Optional<ProductEntity> existOptionalProduct=productRepo.findById(productId);
        if(!existOptionalProduct.isPresent()){
            throw new RuntimeException("Product is not Available With the Name");
        }
        ProductEntity productEntity = existOptionalProduct.get();
        productEntity.setName(updateProdectRequest.getName());
        productEntity.setDescription(updateProdectRequest.getDescription());
        productEntity.setPrice(updateProdectRequest.getPrice());
        productEntity.setStockQuantity(updateProdectRequest.getStockQuantity());
        productEntity.setProductCategory(updateProdectRequest.getProductCategory());
        productEntity.setUpdatedAt(LocalDateTime.now());
        return productRepo.save(productEntity);
    }

    public ProductEntity deleteProductByAdmin(Long productId){
        Optional<ProductEntity> existOptionalProduct=productRepo.findById(productId);
        if(existOptionalProduct.isPresent()){
            ProductEntity productEntity=existOptionalProduct.get();
            productEntity.setDeleted(true);
            productRepo.save(productEntity);
        }
        throw new RuntimeException("Cannot Found Product With the Given ProductId");
    }
}
