package com.example.jobmatch.domain.company.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Company_JobResponse {
    private Integer jobId;
    private String title;
    private String description;
    private String salary;
    private String location;
    private Date annoucementDate;
    private Date expirationDate;
    private String workExperience;
    private List<String> jobsImages;
}
