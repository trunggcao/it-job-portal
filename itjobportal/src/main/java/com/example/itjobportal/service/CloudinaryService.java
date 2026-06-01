package com.example.itjobportal.service;

import com.cloudinary.Cloudinary;
import com.example.itjobportal.config.CloudinaryConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public String uploadFile(MultipartFile file, String folderName){
        if (file.isEmpty()){
            throw new RuntimeException("Empty file");
        }
        try {
            Map<String, Object> options = new HashMap<>();
            // it_job_portal/cvs or it_job_portal/avatars
            options.put("folder", "it_job_portal/" + folderName);

            //
            String contentType = file.getContentType();
            if (contentType != null && contentType.startsWith("image/")) {
                options.put("resource_type", "image");
            } else {
                options.put("resource_type", "raw");
            }

            // Đẩy dữ liệu byte của file lên Cloudinary
            Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), options);

            // Trả về đường link URL (bắt đầu bằng https) của file trên Cloudinary
            return (String) uploadResult.get("secure_url");

        } catch (IOException e) {
            throw new RuntimeException("Lỗi upload file lên Cloudinary: " + e.getMessage());
        }
    }
}
