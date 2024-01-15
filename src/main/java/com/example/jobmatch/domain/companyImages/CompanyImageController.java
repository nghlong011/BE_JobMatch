package com.example.jobmatch.domain.companyImages;

import com.example.jobmatch.domain.jobImages.JobImagesRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/companyImage")
public class CompanyImageController {
    @Autowired
    private CompanyImageService companyImageService;
    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('EMPLOYER','ADMIN')")
    public ResponseEntity create(Principal principal, @ModelAttribute CompanyImageRequest companyImageRequest) {
        return ResponseEntity.ok(companyImageService.createImages(principal, companyImageRequest));
    }

    @PostMapping("/update/{companyImageId}")
    @PreAuthorize("hasAnyRole('EMPLOYER','ADMIN')")
    public ResponseEntity update(@PathVariable("companyImageId") Integer companyImageId, @ModelAttribute CompanyImageRequest companyImageRequest) {
        return ResponseEntity.ok(companyImageService.updateImages(companyImageId, companyImageRequest));
    }
}
