package com.example.jobmatch.domain.jobApplication;

import com.example.jobmatch.domain.jobApplication.dto.JobApplicationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/jobapp")
public class JobApplicationController {
    @Autowired
    private JobApplicationService jobApplicationService;
    @PostMapping("/create")
    public ResponseEntity create(Principal principal, @RequestBody JobApplicationRequest jobApplicationRequest) {
        return ResponseEntity.ok(jobApplicationService.create(principal, jobApplicationRequest));
    }

    @GetMapping("/{jobId}/getUserHaventApply")
    public ResponseEntity getUserHaventApply(@PathVariable("jobId") Integer jobId, @RequestParam(value="page", defaultValue = "0", required = false) int page, @RequestParam(value="limit", defaultValue = "20", required = false) int limit) {
        return ResponseEntity.ok(jobApplicationService.getUserHaventApply(jobId, page,limit));
    }
}
