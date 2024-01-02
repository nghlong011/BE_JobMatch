package com.example.jobmatch.domain.company.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CompanyResponse {
    private Integer companyId;
    private String companyName;
    private String description;
    private String location;
    private String logo;
    private List<Company_JobResponse> jobs;
    private List<String> companyImage;
}
