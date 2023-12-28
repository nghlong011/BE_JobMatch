package com.example.jobmatch.domain.companyImages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/companyImage")
public class CompanyImageController {
    @Autowired
    private CompanyImageService companyImageService;
    @PostMapping("/create")
    public ResponseEntity create(Principal principal, @ModelAttribute CompanyImageRequest companyImageRequest) {
        return ResponseEntity.ok(companyImageService.createImages(principal, companyImageRequest));
    }
}
