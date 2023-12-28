package com.example.jobmatch.domain.companyImages;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class CompanyImageRequest {
    private MultipartFile[] file;
}
