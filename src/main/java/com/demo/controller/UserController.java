package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.demo.dto.ApiResponse;
import com.demo.entity.User;
import com.demo.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Method to get all users
    @GetMapping
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        ApiResponse<List<User>> response = ApiResponse.of(users, "Users retrieved successfully", HttpStatus.OK);
        return ResponseEntity.ok(response); // Wrap the result in ApiResponse
    }

    // Method to get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user != null) {
            ApiResponse<User> response = ApiResponse.of(user, "User retrieved successfully", HttpStatus.OK);
            return ResponseEntity.ok(response);
        } else {
            ApiResponse<User> response = ApiResponse.error("User not found", HttpStatus.NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response); // Return 404 with error response
        }
    }

    // Method to create a new user
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<User>> createUser(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam("image") MultipartFile imageFile) {
        try {

            // Hash the password using BCrypt
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(password);

            // Pass the encoded password to the service
            User user = userService.createUser(name, email, username, encodedPassword, imageFile);

            // User user = userService.createUser(name, email, username, password, imageFile);
            ApiResponse<User> response = ApiResponse.of(user, "User created successfully", HttpStatus.CREATED);
            return ResponseEntity.status(HttpStatus.CREATED).body(response); // Return 201 Created with success message
        } catch (Exception e) {
            ApiResponse<User> response = ApiResponse.error("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // Return 500 with error message
        }
    }
}
