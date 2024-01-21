package com.example.jobmatch.domain.jobApplication;


import com.example.jobmatch.entity.JobApplicationEntity;
import com.example.jobmatch.entity.JobsEntity;
import com.example.jobmatch.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobAppRepo extends JpaRepository<JobApplicationEntity, Integer> {
    boolean existsByJobsEntityAndAndUserEntity(JobsEntity jobsEntity, UserEntity userEntity);

    Integer countAllByStatusAndAndJobsEntity(Integer status, JobsEntity jobsEntity);
}
