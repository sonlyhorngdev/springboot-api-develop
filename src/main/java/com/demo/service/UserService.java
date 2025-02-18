package com.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.demo.entity.User;
import com.demo.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MinioService minioService;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // Create a new user with image upload
    public User createUser(String name, String email, String username, String password, MultipartFile imageFile) {
        try {
            // Use the username as the custom file name
           // String customFileName = username + "-" + imageFile.getOriginalFilename();  // Combine username with the original file name (or you can just use username)
            String customFileName = username;

            // Upload image to MinIO and get the URL, passing the custom file name
            String imageUrl = minioService.uploadFile(imageFile, customFileName);

            // Create user entity
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setUsername(username);
            user.setPassword(password);
            user.setImageUrl(imageUrl);

            // Save user
            return userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Error uploading file: " + e.getMessage());
        }
    }
}
