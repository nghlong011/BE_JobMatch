package com.example.jobmatch.domain.company.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyRequest {
    private String companyName;
    private String description;
    private String location;
}
