package com.example.jobmatch.domain.upload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/")
public class UploadController {
    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam(value = "file") MultipartFile file) {
        return ResponseEntity.ok(uploadService.uploadFile(file));
    }

    @PostMapping("/createBucket")
    public ResponseEntity<?> createBucket(@RequestBody String name) {
        return ResponseEntity.ok(uploadService.createBucket(name));
    }

    @GetMapping("/getListBucket")
    public ResponseEntity<?> getListBucket() {
        return ResponseEntity.ok(uploadService.getListBucket());
    }
}
