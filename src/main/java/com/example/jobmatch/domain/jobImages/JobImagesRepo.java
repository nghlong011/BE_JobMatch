package com.example.jobmatch.domain.jobImages;

import com.example.jobmatch.entity.JobImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobImagesRepo extends JpaRepository<JobImageEntity, Integer> {
}
