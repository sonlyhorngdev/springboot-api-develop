package com.demo.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class FileNameUtil {

    // Helper method to extract the file extension from the original filename
    public static String getFileExtension(String originalFilename) {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        return timestamp + ""+ fileExtension;
    }

    // Helper method to generate a unique filename using UUID
    public static String generateUniqueFileName(String originalFilename) {
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        return UUID.randomUUID().toString() + fileExtension;
    }

    // Helper method to generate a timestamp-based filename
    public static String generateTimestampBasedFileName(String originalFilename) {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        return timestamp + "_" + UUID.randomUUID().toString() + fileExtension;
    }
}
