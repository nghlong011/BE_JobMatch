package com.example.jobmatch.domain.repository;

import com.example.jobmatch.domain.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepo extends JpaRepository<CompanyEntity, Integer> {
}
