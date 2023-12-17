package com.example.jobmatch.domain.company;

import com.example.jobmatch.domain.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepo extends JpaRepository<CompanyEntity, Integer> {
    Boolean existsByCompanyName(String companyName);
    CompanyEntity findByCompanyName(String companyName);
}
