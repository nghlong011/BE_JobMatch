package com.example.jobmatch.domain.company;

import com.example.jobmatch.domain.company.dto.request.CompanyRequest;
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

    @PostMapping("/update")
    public ResponseEntity update(Principal principal, @RequestBody CompanyRequest companyRequest) {
        return ResponseEntity.ok(companyService.update(principal, companyRequest));
    }
}
