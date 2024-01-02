package com.example.jobmatch.domain.job;

import com.example.jobmatch.domain.job.dto.request.CreateJobsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/job")
public class JobsController {
    @Autowired
    private JobsService jobsService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('EMPLOYER')")
    public ResponseEntity create(Principal principal, @RequestBody CreateJobsRequest request) {
        return ResponseEntity.ok(jobsService.createJob(principal, request));
    }

    @GetMapping("/get")
    public ResponseEntity get() {
        return ResponseEntity.ok(jobsService.getListJob());
    }

    @GetMapping("/delete/{jobId}")
    @PreAuthorize("hasAnyRole('EMPLOYER')")
    public ResponseEntity delete( @PathVariable("jobId") Integer jobId) {
        return ResponseEntity.ok(jobsService.deleteJob(jobId));
    }

    @PostMapping("/update/{jobId}")
    @PreAuthorize("hasAnyRole('EMPLOYER')")
    public ResponseEntity update( @PathVariable("jobId") Integer jobId, @RequestBody CreateJobsRequest request) {
        return ResponseEntity.ok(jobsService.updateJob(jobId, request));
    }

    @GetMapping("/getJobFromEmail")
    @PreAuthorize("hasAnyRole('EMPLOYER')")
    public ResponseEntity get(Principal principal) {
        return ResponseEntity.ok(jobsService.getListJob(principal));
    }

    @GetMapping("/getByTitle/{title}")
    public ResponseEntity getByTitle(@PathVariable String title) {
        return ResponseEntity.ok(jobsService.getByTitle(title));
    }
    @GetMapping("/getListJob")
    public ResponseEntity getListJob() {
        return ResponseEntity.ok(jobsService.getListJob());
    }
}
