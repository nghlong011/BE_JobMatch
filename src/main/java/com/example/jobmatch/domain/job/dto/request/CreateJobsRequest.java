package com.example.jobmatch.domain.job.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Getter
@Setter
public class CreateJobsRequest {
    private String title;
    private MultipartFile description;
    private String salary;
    private String location;
    private Date expirationDate;
    private String workExperience;
    private Integer categoryId;
    private Integer status;
    private Integer numberRecruit;
}
