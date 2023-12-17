package com.example.jobmatch.domain.categoryImages;

import com.example.jobmatch.domain.entity.CompanyImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyImagesRepo extends JpaRepository<CompanyImageEntity, Integer> {
}
