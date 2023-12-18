package com.example.jobmatch.domain.jobApplication.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobApplicationRequest {
    Integer jobId;
    String content;
    String status;

}
