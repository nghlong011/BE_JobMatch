package com.example.jobmatch.domain.jobApplication;


import com.example.jobmatch.entity.JobApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobAppRepo extends JpaRepository<JobApplicationEntity, Integer> {
}
