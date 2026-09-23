package com.pms.political_management_system.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class FileStorageService {

    private static final List<String> ALLOWED_TYPES = List.of(
            "image/jpeg", "image/jpg", "image/png", "image/webp", "image/gif"
    );

    private static final long MAX_SIZE_BYTES = 5L * 1024 * 1024; // 5MB

    public String storeImage(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new RuntimeException("No file provided");
        }

        if (file.getSize() > MAX_SIZE_BYTES) {
            throw new RuntimeException("File size must be under 5MB");
        }

        String contentType = file.getContentType();

        if (contentType == null || !ALLOWED_TYPES.contains(contentType.toLowerCase())) {
            throw new RuntimeException("Only JPG, PNG, WEBP or GIF images are allowed");
        }

        try {
            Path uploadDir = Paths.get("uploads").toAbsolutePath();

            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            String originalName = file.getOriginalFilename() != null ? file.getOriginalFilename() : "image";
            String extension = "";

            int dotIndex = originalName.lastIndexOf('.');
            if (dotIndex >= 0) {
                extension = originalName.substring(dotIndex);
            }

            String fileName = UUID.randomUUID() + extension;
            Path targetPath = uploadDir.resolve(fileName);

            try (InputStream in = file.getInputStream();
                 OutputStream out = Files.newOutputStream(targetPath)) {
                in.transferTo(out);
            }

            return "/uploads/" + fileName;

        } catch (IOException e) {
            throw new RuntimeException("Failed to store file: " + e.getMessage());
        }
    }
}
