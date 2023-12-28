package com.example.jobmatch.domain.company.dto.response;

import com.example.jobmatch.domain.entity.CategoryEntity;
import com.example.jobmatch.domain.entity.JobsEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class JobsEntityDTO {
    private Integer jobId;
    private String title;
    private String description;
    private String salary;
    private String location;
    private Date annoucementDate;
    private Date expirationDate;
    private String workExperience;
    private Integer companyId;
    private List<Integer> categoryIds; // Assuming you want to expose category IDs

    // Constructors, getters, setters
    // ...

    public static JobsEntityDTO fromEntity(JobsEntity jobsEntity) {
        JobsEntityDTO dto = new JobsEntityDTO();
        dto.setCompanyId(jobsEntity.getCompanyEntity().getCompanyId());
        dto.setJobId(jobsEntity.getJobId());
        dto.setTitle(jobsEntity.getTitle());
        dto.setDescription(jobsEntity.getDescription());
        dto.setSalary(jobsEntity.getSalary());
        dto.setLocation(jobsEntity.getLocation());
        dto.setAnnoucementDate(jobsEntity.getAnnoucementDate());
        dto.setExpirationDate(jobsEntity.getExpirationDate());
        dto.setWorkExperience(jobsEntity.getWorkExperience());
        dto.setCategoryIds(
                jobsEntity.getCategoryEntities().stream()
                        .map(CategoryEntity::getCategoryId)
                        .collect(Collectors.toList())
        );
        return dto;
    }
}
