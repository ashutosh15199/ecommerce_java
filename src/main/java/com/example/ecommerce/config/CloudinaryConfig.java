package com.example.ecommerce.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class CloudinaryConfig {

@Bean
    public Cloudinary cloudinary(){
    Map<String,String> image = new HashMap<>();
    image.put("cloud_name","dnoo8zscl");
    image.put("api_key","677699873713696");
    image.put("api_secret","SuwKaaWfFSbF8sWtdK4cqf4E0Os");
    return new Cloudinary(image);
}
}
