package com.example.itjobportal.controller;

import com.example.itjobportal.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/test-upload")
@RequiredArgsConstructor
public class TestUploadfile {
    private final CloudinaryService cloudinaryService;

    @PostMapping
    public ResponseEntity<?> testUpload(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "folder", defaultValue = "general_test") String folder) {


        String fileUrl = cloudinaryService.uploadFile(file, folder);

        // Trả kết quả về cho client
        return ResponseEntity.ok(Map.of(
                "status", "Thành công",
                "folder_saved", "it_job_portal/" + folder,
                "file_url", fileUrl
        ));
    }
}
