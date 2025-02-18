package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // Create new user with image upload
    // @PostMapping("/create")
    // public ResponseEntity<User> createUser(
    //         @RequestParam String name,
    //         @RequestParam String email,
    //         @RequestParam String username,
    //         @RequestParam String password,
    //         @RequestParam("image") MultipartFile imageFile) {
    //     try {
    //         User user = userService.createUser(name, email, username, password, imageFile);
    //         return ResponseEntity.ok(user);
    //     }
    // }
    @PostMapping("/create")
    public ResponseEntity<User> createUser(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam("image") MultipartFile imageFile) {
        try {
            User user = userService.createUser(name, email, username, password, imageFile);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
