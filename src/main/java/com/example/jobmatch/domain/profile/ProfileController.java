package com.example.jobmatch.domain.profile;

import com.example.jobmatch.domain.company.dto.request.CompanyRequest;
import com.example.jobmatch.domain.profile.dto.request.ProfileRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;
    @PostMapping("/create")
    public ResponseEntity create(Principal principal, @RequestBody ProfileRequest profileRequest) {
        return ResponseEntity.ok(profileService.create(principal, profileRequest));
    }
    @PostMapping("/update")
    public ResponseEntity update(Principal principal, @RequestBody ProfileRequest profileRequest) {
        return ResponseEntity.ok(profileService.update(principal, profileRequest));
    }
}
