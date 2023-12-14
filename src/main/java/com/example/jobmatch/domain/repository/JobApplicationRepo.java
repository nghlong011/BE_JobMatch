package com.example.jobmatch.domain.repository;

import com.example.jobmatch.domain.entity.JobApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobApplicationRepo extends JpaRepository<JobApplicationEntity, Integer> {
}
