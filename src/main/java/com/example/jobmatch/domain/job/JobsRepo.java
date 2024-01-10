package com.example.jobmatch.domain.job;

import com.example.jobmatch.entity.JobsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobsRepo extends JpaRepository<JobsEntity, Integer> {
    void deleteByJobId(Integer jobId);
    @Query(value = "SELECT * FROM JOBS WHERE JOBS.TITLE LIKE %?%",nativeQuery = true)
    List<JobsEntity> findByTitle (String title);

    @Query(value = "SELECT j.* FROM JOBS j JOIN JOBS_APPLICATION ja ON (j.JOB_ID = ja.JOB_ID) JOIN USER u ON (u.USER_ID = ja.USER_ID) WHERE u.EMAIL = ?", nativeQuery = true)
    List<JobsEntity> getJobApplyByUser(String email);

}
