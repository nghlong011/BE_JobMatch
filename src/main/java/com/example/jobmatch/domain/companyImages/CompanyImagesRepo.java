package com.example.jobmatch.domain.companyImages;

import com.example.jobmatch.entity.CompanyImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyImagesRepo extends JpaRepository<CompanyImageEntity, Integer> {
}
