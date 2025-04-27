package com.example.ecommerce.controller;
import com.example.ecommerce.entities.ProductCategory;
import com.example.ecommerce.entities.ProductEntity;
import com.example.ecommerce.services.ImageUploadService;
import com.example.ecommerce.services.ProductServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/product")
public class ProductController {
    private final ImageUploadService imageUploadService;
    private final ProductServices productServices;
    public ProductController(ImageUploadService imageUploadService, ProductServices productServices) {
        this.imageUploadService = imageUploadService;
        this.productServices = productServices;
    }
    @GetMapping("/get_products")
    public ResponseEntity<List<ProductEntity>> getAllproducts() {
        return ResponseEntity.ok(productServices.getProductByAdmin());
    }
    @GetMapping("/get_product/{productId}")
    public ResponseEntity<ProductEntity> getProductById(@PathVariable Long productId) {
        ProductEntity productEntity = productServices.getProductById(productId);
        return ResponseEntity.ok(productEntity);
    }
    @GetMapping("/search")
    public ResponseEntity<List<ProductEntity>> searchProduct(@RequestParam String query){
        List<ProductEntity> products = productServices.searchProduct(query);
        return ResponseEntity.ok(products);
 }
 @GetMapping("/get_products_by_category")
    public ResponseEntity<Map<ProductCategory,List<ProductEntity>>> getproducts(){
        Map<ProductCategory,List<ProductEntity>> products = productServices.getProductsGroupedByCategory();
        return ResponseEntity.ok(products);
 }
}
