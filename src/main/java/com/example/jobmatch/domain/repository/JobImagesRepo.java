package com.example.jobmatch.domain.repository;

import com.example.jobmatch.domain.entity.JobImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobImagesRepo extends JpaRepository<JobImageEntity, Integer> {
}
