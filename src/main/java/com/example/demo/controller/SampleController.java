package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ApiResponse;

@RestController
@RequestMapping("/api")
public class SampleController {

    // For Example
    @GetMapping("/success")
    public ResponseEntity<ApiResponse<String>> getSuccess() {
        return ResponseEntity.ok(ApiResponse.of("Hello, World!", "Request successful", HttpStatus.OK));
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<String>> createResource() {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.of("Resource Created", "Successfully created", HttpStatus.CREATED));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<String>> deleteResource() {
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(ApiResponse.of(null, "Resource deleted successfully", HttpStatus.NO_CONTENT));
    }

    @GetMapping("/error")
    public ResponseEntity<ApiResponse<String>> getError() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
