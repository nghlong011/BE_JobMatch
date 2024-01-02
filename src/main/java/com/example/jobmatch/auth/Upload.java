package com.example.jobmatch.auth;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Component
public class Upload {
    public String createImages(MultipartFile files, String partRoot) {
        try {
            String fileName = files.getOriginalFilename();
            String extension = FilenameUtils.getExtension(fileName);
            String newNameFile = (UUID.randomUUID()) + "." + extension;
            Path target = Paths.get(partRoot + "/" + newNameFile);
            Files.copy(files.getInputStream(), Paths.get(partRoot + "/" + fileName), StandardCopyOption.REPLACE_EXISTING);
            Files.move(Paths.get(partRoot + "/" + fileName), target);
            return newNameFile;
        } catch (IOException e) {
            return "Upload thất bại";
        }
    }
}
