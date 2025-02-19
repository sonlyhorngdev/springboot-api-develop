package com.demo.service;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.errors.MinioException;

@Service
public class MinioService {

    private final MinioClient minioClient;

    @Value("${minio.bucketName}")
    private String bucketName;

    @Value("${minio.url}")
    private String minioUrl;

    public MinioService(@Value("${minio.url}") String minioUrl,
            @Value("${minio.accessKey}") String accessKey,
            @Value("${minio.secretKey}") String secretKey) {
        this.minioClient = MinioClient.builder()
                .endpoint(minioUrl)
                .credentials(accessKey, secretKey)
                .build();
    }

    // Upload file with an optional custom file name and date-based folder
    public String uploadFile(MultipartFile file, String customFileName) throws Exception {
        try {
            String folderName = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            String fileName = (customFileName != null && !customFileName.isEmpty())
                    ? customFileName
                    : UUID.randomUUID() + "-" + file.getOriginalFilename();
            String objectName = folderName + "/" + fileName;
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );

            return minioUrl + "/" + bucketName + "/" + objectName;  // Return the public URL
        } catch (MinioException e) {
            throw new RuntimeException("Error uploading file: " + e.getMessage(), e);
        }
    }

    // Get file from MinIO by file name
    public InputStream getFile(String fileName) throws Exception {
        try {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .build()
            );
        } catch (MinioException e) {
            throw new RuntimeException("Error retrieving file: " + e.getMessage(), e);
        }
    }

    // Delete file from MinIO by file name
    public void deleteFile(String fileName) throws Exception {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .build()
            );
        } catch (MinioException e) {
            throw new RuntimeException("Error deleting file: " + e.getMessage(), e);
        }
    }

    // Upload file with an optional custom file name with out generat folder
    // public String uploadFile(MultipartFile file, String customFileName) throws Exception {
    //     try {
    //         // If customFileName is not provided, use UUID + original file name
    //         String fileName = (customFileName != null && !customFileName.isEmpty())
    //                 ? customFileName
    //                 : UUID.randomUUID() + "-" + file.getOriginalFilename();
    //         minioClient.putObject(
    //                 PutObjectArgs.builder()
    //                         .bucket(bucketName)
    //                         .object(fileName)
    //                         .stream(file.getInputStream(), file.getSize(), -1)
    //                         .contentType(file.getContentType())
    //                         .build()
    //         );
    //         return minioUrl + "/" + bucketName + "/" + fileName;  // Public URL
    //     } catch (MinioException e) {
    //         throw new RuntimeException("Error uploading file: " + e.getMessage(), e);
    //     }
    // }
}
