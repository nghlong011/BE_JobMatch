package com.example.jobmatch.domain.profile;

import com.example.jobmatch.domain.profile.dto.request.ProfileRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;
    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('JOB_SEEKER','ADMIN')")
    public ResponseEntity create(Principal principal, @ModelAttribute ProfileRequest profileRequest) {
        return ResponseEntity.ok(profileService.create(principal, profileRequest));
    }
    @PostMapping("/update")
    @PreAuthorize("hasAnyRole('JOB_SEEKER','ADMIN')")
    public ResponseEntity update(Principal principal, @RequestBody ProfileRequest profileRequest) {
        return ResponseEntity.ok(profileService.update(principal, profileRequest));
    }
    @PostMapping("/get")
    @PreAuthorize("hasAnyRole('JOB_SEEKER', 'EMPLOYER','ADMIN')")
    public ResponseEntity get(@RequestBody ProfileRequest profileRequest) {
        return ResponseEntity.ok(profileService.get(profileRequest));
    }

}
