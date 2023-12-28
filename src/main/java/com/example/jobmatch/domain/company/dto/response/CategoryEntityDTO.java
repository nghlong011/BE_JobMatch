package com.example.jobmatch.domain.company.dto.response;

import com.example.jobmatch.domain.entity.CategoryEntity;
import com.example.jobmatch.domain.entity.JobsEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class CategoryEntityDTO {
    private Integer categoryId;
    private String categoryName;
    private List<Integer> jobIds; // Assuming you want to expose job IDs

    // Constructors, getters, setters
    // ...

    public static CategoryEntityDTO fromEntity(CategoryEntity categoryEntity) {
        CategoryEntityDTO dto = new CategoryEntityDTO();
        dto.setCategoryId(categoryEntity.getCategoryId());
        dto.setCategoryName(categoryEntity.getCategoryName());
        dto.setJobIds(
                categoryEntity.getJobsEntities().stream()
                        .map(JobsEntity::getJobId)
                        .collect(Collectors.toList())
        );
        return dto;
    }
}
