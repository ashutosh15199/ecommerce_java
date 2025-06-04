package com.example.ecommerce.services;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageUploadService {
    public List<String> uploadImage(Long productId, MultipartFile[] files) throws IOException;
}
