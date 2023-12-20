package com.example.jobmatch.domain.category;

import com.example.jobmatch.domain.entity.CategoryEntity;
import com.example.jobmatch.domain.entity.CompanyImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
}
