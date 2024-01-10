package com.example.jobmatch.domain.company.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class CompanyRequest {
    private String companyName;
    private MultipartFile description;
    private String location;
    private MultipartFile logo;
}
