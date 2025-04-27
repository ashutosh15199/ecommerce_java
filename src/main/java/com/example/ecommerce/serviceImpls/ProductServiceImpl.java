package com.example.ecommerce.serviceImpls;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.ecommerce.entities.ProductCategory;
import com.example.ecommerce.entities.ProductEntity;
import com.example.ecommerce.modals.ProductRequest;
import com.example.ecommerce.modals.UpdateProdectRequest;
import com.example.ecommerce.repository.ProductRepo;
import com.example.ecommerce.services.ProductServices;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductServices{

    private final ProductRepo productRepo;
    private final Cloudinary cloudinary;

    public ProductServiceImpl(ProductRepo productRepo, Cloudinary cloudinary) {
        this.productRepo = productRepo;
        this.cloudinary = cloudinary;
    }


    public ProductEntity addProductByAdmin(ProductRequest productRequest, MultipartFile[] imageUrls) throws IOException {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(productRequest.getName());
        productEntity.setDescription(productRequest.getDescription());
        productEntity.setPrice(productRequest.getPrice());
        productEntity.setStockQuantity(productRequest.getStockQuantity());
        productEntity.setProductCategory(productRequest.getProductCategory());

        // Upload images to Cloudinary and get URLs
        List<String> uploadedImageUrls = new ArrayList<>();
        for (MultipartFile file : imageUrls) {  // Now using correct parameter name
            if (!file.isEmpty()) {
                Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
                String imageUrl = uploadResult.get("secure_url").toString();
                uploadedImageUrls.add(imageUrl);
            }
        }

        // Save image URLs in product entity
        productEntity.setImageUrls(uploadedImageUrls);  // Ensure imageUrls is a List<String> in ProductEntity

        return productRepo.save(productEntity);
    }


    public List<ProductEntity> getProductByAdmin(){
        return productRepo.findAll();
    }
    public ProductEntity getProductById(Long productId){
        return productRepo.findByProductId(productId);
    }

    public ProductEntity updateProductDetailsByAdmin(UpdateProdectRequest updateProdectRequest, Long productId) throws IOException {
        Optional<ProductEntity> existOptionalProduct = productRepo.findById(productId);
        if (!existOptionalProduct.isPresent()) {
            throw new RuntimeException("Product is not Available With the Given ID");
        }

        ProductEntity productEntity = existOptionalProduct.get();
        productEntity.setName(updateProdectRequest.getName());
        productEntity.setDescription(updateProdectRequest.getDescription());
        productEntity.setPrice(updateProdectRequest.getPrice());
        productEntity.setStockQuantity(updateProdectRequest.getStockQuantity());
        productEntity.setProductCategory(updateProdectRequest.getProductCategory());
        productEntity.setUpdatedAt(LocalDateTime.now());

        // ✅ Check if images exist
        if (updateProdectRequest.getImageUrls() != null && updateProdectRequest.getImageUrls().length > 0) {
            List<String> imageUrls = new ArrayList<>();
            for (MultipartFile file : updateProdectRequest.getImageUrls()) {  // ✅ Use getImageURLs()
                if (!file.isEmpty()) {
                    Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
                    String imageUrl = uploadResult.get("secure_url").toString();
                    imageUrls.add(imageUrl);
                }
            }
            productEntity.setImageUrls(imageUrls);  // ✅ Update images
        }

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


    @Override
    public List<ProductEntity> searchProduct(String query) {
        String finalSearch = query.trim().toUpperCase();
        try{
            ProductCategory category = ProductCategory.valueOf(finalSearch);
            return productRepo.findByProductCategory(category);
        }catch (IllegalArgumentException e){
            return productRepo.findByNameContainingIgnoreCase(query);
        }
    }
    @Transactional
    @Override
    public Map<ProductCategory, List<ProductEntity>> getProductsGroupedByCategory() {
        List<ProductEntity> products = productRepo.findAll();
        return products.stream().collect(Collectors.groupingBy(ProductEntity::getProductCategory));
    }

}
