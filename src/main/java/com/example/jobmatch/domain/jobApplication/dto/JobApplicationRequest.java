package com.example.jobmatch.domain.jobApplication.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class JobApplicationRequest {
    private Integer jobAppId;
    private Integer jobId;
    private MultipartFile content;
    private String status;
}
