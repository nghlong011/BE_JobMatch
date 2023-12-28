package com.example.jobmatch.domain.job;

import com.example.jobmatch.domain.entity.JobsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobsRepo extends JpaRepository<JobsEntity, Integer> {
    void deleteByJobId(Integer jobId);
}
