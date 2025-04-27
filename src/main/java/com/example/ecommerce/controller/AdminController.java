package com.example.ecommerce.controller;

import com.example.ecommerce.entities.ProductEntity;
import com.example.ecommerce.entities.Role;
import com.example.ecommerce.entities.User;
import com.example.ecommerce.modals.ProductRequest;
import com.example.ecommerce.modals.UpdateProdectRequest;
import com.example.ecommerce.modals.UpdateRoleRequest;
import com.example.ecommerce.modals.UserRequest;
import com.example.ecommerce.services.ImageUploadService;
import com.example.ecommerce.services.ProductServices;
import com.example.ecommerce.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/admin")  // Fixed the incorrect usage
public class AdminController {  // Renamed class to follow naming conventions
    private final ImageUploadService imageUploadService;
    private final UserService userService;
    private final ProductServices productServices;
    public AdminController(ImageUploadService imageUploadService, UserService userService, ProductServices productServices) {
        this.imageUploadService = imageUploadService;
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
    public ResponseEntity<ProductEntity> addProduct(
            @ModelAttribute ProductRequest productRequest,
            @RequestParam("imageUrls") MultipartFile[] imageUrls, HttpServletRequest request) {  // Now matching parameter name
        System.out.println("Auth Header: " + request.getHeader("Authorization"));
        try {
            ProductEntity product = productServices.addProductByAdmin(productRequest, imageUrls);
            return ResponseEntity.ok(product);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("update_product")
    public ResponseEntity<ProductEntity> updateProductDetailsByAdmin(
            @ModelAttribute UpdateProdectRequest updateProdectRequest
    ) throws IOException {
        Long productId = updateProdectRequest.getProductId();
        ProductEntity productEntity = productServices.updateProductDetailsByAdmin(updateProdectRequest, productId);
        return ResponseEntity.ok(productEntity);
    }

    @DeleteMapping("delete_product/{productId}")
    public ResponseEntity<ProductEntity> deleteProductByAdmin(@PathVariable Long productId) {
        ProductEntity productEntity = productServices.deleteProductByAdmin(productId);
        return ResponseEntity.ok(productEntity);
    }
    @PostMapping("/{productId}/upload_images")
    public ResponseEntity<Map<String, Object>> uploadImage(
            @PathVariable Long productId,
            @RequestParam("file") MultipartFile[] files) {
        try {
            List<String> imageUrls = imageUploadService.uploadImage(productId, files);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Images uploaded successfully");
            response.put("imageUrls", imageUrls);

            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Image upload failed", "message", e.getMessage()));
        }
    }

}
