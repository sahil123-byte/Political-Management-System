package com.pms.political_management_system.controller;

import com.pms.political_management_system.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/upload")
public class FileUploadController {

    @Autowired
    private FileStorageService fileStorageService;

    // Upload an image (profile photo, party logo, member/voter photo, etc.)
    // Returns a relative URL like "/uploads/xxxxx.jpg" which can be prefixed with
    // the backend base URL on the frontend to display it.
    @PostMapping("/image")
    public Map<String, String> uploadImage(@RequestParam("file") MultipartFile file) {

        String url = fileStorageService.storeImage(file);

        return Map.of("url", url);
    }
}
