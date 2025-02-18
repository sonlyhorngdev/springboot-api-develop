package com.demo.controller;

import java.io.InputStream;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.demo.service.MinioService;

@RestController
@RequestMapping("/api/minio")
public class MinioController {

    private final MinioService minioService;

    public MinioController(MinioService minioService) {
        this.minioService = minioService;
    }

    // Upload file with custom file name
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,
                                             @RequestParam("customFileName") String customFileName) {
        try {
            String fileUrl = minioService.uploadFile(file, customFileName);  // Pass custom file name
            return ResponseEntity.ok(fileUrl);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    // Fetch file by custom file name
    @GetMapping("/file/{fileName}")
    public ResponseEntity<byte[]> getFile(@PathVariable String fileName) {
        try {
            InputStream fileStream = minioService.getFile(fileName);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)  // Adjust content type as needed
                    .body(fileStream.readAllBytes());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
