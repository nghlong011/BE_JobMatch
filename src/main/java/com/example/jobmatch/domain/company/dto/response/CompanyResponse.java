package com.example.jobmatch.domain.company.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyResponse {
    private Integer companyId;
    private String companyName;
    private String description;
    private String location;
    private String logo;
    private List<Company_JobResponse> jobs;
    private List<String> companyImage;
}
