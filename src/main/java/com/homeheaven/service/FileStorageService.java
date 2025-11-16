package com.homeheaven.service;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${app.upload.dir}")
    private String uploadDir;

    public String save(MultipartFile file) throws IOException {
        if(file == null || file.isEmpty()) return null;
        File dir = new File(uploadDir);
        if(!dir.exists()) dir.mkdirs();
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        String filename = UUID.randomUUID().toString() + (ext != null && !ext.isEmpty() ? ("." + ext) : "");
        Path target = Paths.get(uploadDir).resolve(filename);
        Files.copy(file.getInputStream(), target);
        return filename;
    }
}
