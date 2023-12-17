package com.example.jobmatch.domain.job;

import com.example.jobmatch.domain.entity.JobsEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface JobsRepo extends JpaRepository<JobsEntity, Integer> {
    void deleteByJobId(Integer jobId);
}
