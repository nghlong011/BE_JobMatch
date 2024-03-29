package com.example.jobmatch.domain.jobApplication;

import com.example.jobmatch.domain.jobApplication.dto.JobApplicationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/jobapp")
public class JobApplicationController {
    @Autowired
    private JobApplicationService jobApplicationService;
    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('JOB_SEEKER','ADMIN')")
    public ResponseEntity create(Principal principal, @ModelAttribute JobApplicationRequest jobApplicationRequest) {
        return ResponseEntity.ok(jobApplicationService.create(principal, jobApplicationRequest));
    }
    @PostMapping("/update")
    @PreAuthorize("hasAnyRole('EMPLOYER','ADMIN')")
    public ResponseEntity update(@ModelAttribute JobApplicationRequest jobApplicationRequest) {
        return ResponseEntity.ok(jobApplicationService.update(jobApplicationRequest));
    }

    @GetMapping("/getUserHaventApply/{jobId}")
    @PreAuthorize("hasAnyRole('EMPLOYER','ADMIN')")
    public ResponseEntity getUserHaventApply(@PathVariable("jobId") Integer jobId) {
        return ResponseEntity.ok(jobApplicationService.getUserHaventApply(jobId));
    }
}
