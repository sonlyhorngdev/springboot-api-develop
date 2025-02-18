package com.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.demo.entity.User;
import com.demo.repository.UserRepository;
import com.demo.util.FileNameUtil;

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

            // Use the utility class to generate a custom file name (timestamp-based or unique)
            String customFileName = FileNameUtil.generateTimestampBasedFileName(imageFile.getOriginalFilename()); // Or use generateUniqueFileName()

            // Upload image to MinIO and get the URL
            String imageUrl = minioService.uploadFile(imageFile, customFileName);

            // Create user entity and set properties
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setUsername(username);
            user.setPassword(password);
            user.setImageUrl(imageUrl);

            // Save and return the created user
            return userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Error uploading file: " + e.getMessage());
        }
    }
}
