package com.example.ecommerce.serviceImpls;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.ecommerce.entities.ProductEntity;
import com.example.ecommerce.repository.ProductRepo;
import com.example.ecommerce.services.ImageUploadService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ImageUploadServiceImpl implements ImageUploadService {
    private final Cloudinary cloudinary;
    private final ProductRepo productRepo;
    public  ImageUploadServiceImpl(Cloudinary cloudinary1, ProductRepo productRepo){

        this.cloudinary = cloudinary1;
        this.productRepo = productRepo;

    }
    @Override
    @Transactional
    public List<String> uploadImage(Long productId, MultipartFile[] files) throws IOException {
        ProductEntity products = productRepo.findByProductId(productId);
        if(products== null){
            throw new RuntimeException("Product Id is not found!");
        }
        List<String> imageUrls = new ArrayList<>();
        for(MultipartFile file : files){
            if (!file.isEmpty()) {
                Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
                String imageUrl = uploadResult.get("secure_url").toString();
                imageUrls.add(imageUrl);
            }
        }

        products.getImageUrls().addAll(imageUrls);
        productRepo.save(products);
        return imageUrls;


    }
}
