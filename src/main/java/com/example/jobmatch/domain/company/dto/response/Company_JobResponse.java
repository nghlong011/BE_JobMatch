package com.example.jobmatch.domain.company.dto.response;

import com.example.jobmatch.entity.CompanyEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Company_JobResponse {
    private Integer jobId;
    private CompanyEntity companyEntity;
    private String title;
    private String description;
    private String salary;
    private String location;
    private Date annoucementDate;
    private Date expirationDate;
    private String workExperience;
    private List<String> jobsImages;
}
