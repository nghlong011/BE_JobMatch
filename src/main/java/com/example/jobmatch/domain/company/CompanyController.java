package com.example.jobmatch.domain.company;

import com.example.jobmatch.domain.company.dto.request.CompanyRequest;
import com.example.jobmatch.domain.job.dto.request.CreateJobsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    private CompanyService companyService;
    @GetMapping("/getCompany")
    public ResponseEntity get() {
        return ResponseEntity.ok(companyService.getAll());
    }
    @PostMapping("/create")
    public ResponseEntity create(Principal principal, @RequestBody CompanyRequest companyRequest) {
        return ResponseEntity.ok(companyService.create(principal, companyRequest));
    }

    @PostMapping("/update/{companyId}")
    public ResponseEntity update( @PathVariable("companyId") Integer companyId, @RequestBody CompanyRequest companyRequest) {
        return ResponseEntity.ok(companyService.update(companyId, companyRequest));
    }
}
