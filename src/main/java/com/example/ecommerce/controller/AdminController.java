package com.example.ecommerce.controller;

import com.example.ecommerce.entities.ProductEntity;
import com.example.ecommerce.entities.Role;
import com.example.ecommerce.entities.User;
import com.example.ecommerce.modals.ProductRequest;
import com.example.ecommerce.modals.UpdateProdectRequest;
import com.example.ecommerce.modals.UpdateRoleRequest;
import com.example.ecommerce.modals.UserRequest;
import com.example.ecommerce.services.ProductServices;
import com.example.ecommerce.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")  // Fixed the incorrect usage
public class AdminController {  // Renamed class to follow naming conventions

    private final UserService userService;
    private final ProductServices productServices;
    public AdminController(UserService userService, ProductServices productServices) {
        this.userService = userService;
        this.productServices = productServices;
    }

    @GetMapping("/demo")
    public ResponseEntity<String> demo() {
        return ResponseEntity.ok("Hello from secured URL");
    }

    @PostMapping("/create_user")
    public ResponseEntity<String> createUserByAdmin(@RequestBody UserRequest userRequest) {
        userService.createUserByAdmin(userRequest);
        return new ResponseEntity<>("User Created Successfully", HttpStatus.CREATED);
    }

    @GetMapping("/get_all_users")
    public ResponseEntity<List<User>> getAllUsersByAdmin() {  // Fixed method name consistency
        return ResponseEntity.ok(userService.getAllUsersByAdmin());
    }

    @PutMapping("/update-role")
    public ResponseEntity<User> updateUserRole(@RequestBody UpdateRoleRequest updateRoleRequest) {
        User updatedUser = userService.updateUserRoleByAdmin(updateRoleRequest.getId(), updateRoleRequest.getRole());
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/delete_user/{id}")
    public ResponseEntity<User> deleteUserByAdmin(@PathVariable Long id) {
        User user = userService.softDeleteByAdmin(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/get_user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }
    @PostMapping("/add_product")
    public ResponseEntity<ProductEntity> addProduct(@RequestBody ProductRequest productRequest ) {
        ProductEntity createdProduct = productServices.addProductByAdmin(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }
    @GetMapping("/get_products")
    public ResponseEntity<List<ProductEntity>> getAllproducts(){
        return  ResponseEntity.ok(productServices.getProductByAdmin());
    }
    @GetMapping("/get_product/{productId}")
    public ResponseEntity<ProductEntity> getProductById(@PathVariable Long productId){
        ProductEntity productEntity = productServices.getProductById(productId);
        return ResponseEntity.ok(productEntity);
    }
    @PutMapping("update_product")
    public ResponseEntity<ProductEntity> updateProductDetailsByAdmin( @RequestBody UpdateProdectRequest updateProdectRequest){
        Long productId = updateProdectRequest.getProductId();
        ProductEntity productEntity = productServices.updateProductDetailsByAdmin( updateProdectRequest,productId);
        return ResponseEntity.ok(productEntity);
    }
    @DeleteMapping("delete_product/{productId}")
    public ResponseEntity<ProductEntity> deleteProductByAdmin(@PathVariable Long productId){
        ProductEntity productEntity = productServices.deleteProductByAdmin(productId);
        return ResponseEntity.ok(productEntity);
    }
}
