package com.example.jobmatch.domain.jobImages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/jobImages")
public class JobImageController {
    @Autowired
    private JobImageService jobImageService;
    @PostMapping("/create")
    public ResponseEntity create(@ModelAttribute JobImagesRequest jobImagesRequest) {
        return ResponseEntity.ok(jobImageService.createImages(jobImagesRequest));
    }
}
