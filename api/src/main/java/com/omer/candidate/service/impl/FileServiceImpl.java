package com.omer.candidate.service.impl;

import com.omer.candidate.service.IFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileServiceImpl implements IFileService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = Paths.get(System.getProperty("user.dir"), uploadDir).resolve(fileName).normalize();
            if (!filePath.startsWith(Paths.get(System.getProperty("user.dir"), uploadDir))) {
                throw new RuntimeException("Unauthorized access attempt: " + fileName);
            }
            Resource resource = new UrlResource(filePath.toUri());
            if (!resource.exists() || !resource.isReadable()) {
                throw new RuntimeException("Could not read the file: " + fileName);
            }
            return resource;
        } catch (Exception e) {
            throw new RuntimeException("Error while loading file: " + e.getMessage());
        }
    }
}
