package com.example.jobmatch.domain.company;

import com.example.jobmatch.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepo extends JpaRepository<CompanyEntity, Integer> {
    Boolean existsByCompanyName(String companyName);

    @Query(value = "SELECT COMPANY.* FROM COMPANY JOIN JOBS\n" +
            "ON COMPANY.COMPANY_ID = JOBS.COMPANY_ID\n" +
            "WHERE JOBS.TITLE LIKE %?%",nativeQuery = true)
    List<CompanyEntity> findByTitle (String title);
}
