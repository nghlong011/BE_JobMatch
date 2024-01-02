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

}
