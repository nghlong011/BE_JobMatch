package com.example.jobmatch.domain.company;

import com.example.jobmatch.domain.company.dto.request.CompanyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyRole('EMPLOYER','ADMIN')")
    public ResponseEntity create(Principal principal, @ModelAttribute CompanyRequest companyRequest) {
        return ResponseEntity.ok(companyService.create(principal, companyRequest));
    }

    @PostMapping("/update")
    @PreAuthorize("hasAnyRole('EMPLOYER','ADMIN')")
    public ResponseEntity update(Principal principal, @RequestBody CompanyRequest companyRequest) {
        return ResponseEntity.ok(companyService.update(principal, companyRequest));
    }

    @GetMapping("/getCompanyByTitle/{title}")
    public ResponseEntity getByTitle(@PathVariable String title) {
        return ResponseEntity.ok(companyService.getByTitle(title));
    }
}
