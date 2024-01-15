package com.example.jobmatch.domain.jobImages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/jobImages")
public class JobImageController {
    @Autowired
    private JobImageService jobImageService;
    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('EMPLOYER','ADMIN')")
    public ResponseEntity create(@ModelAttribute JobImagesRequest jobImagesRequest) {
        return ResponseEntity.ok(jobImageService.createImages(jobImagesRequest));
    }

    @PostMapping("/update/{jobImageId}")
    @PreAuthorize("hasAnyRole('EMPLOYER','ADMIN')")
    public ResponseEntity update(@PathVariable("jobImageId") Integer jobImageId, @ModelAttribute JobImagesRequest jobImagesRequest) {
        return ResponseEntity.ok(jobImageService.updateImages(jobImageId, jobImagesRequest));
    }
}
