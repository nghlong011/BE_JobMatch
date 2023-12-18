package com.example.jobmatch.domain.jobApplication;

import com.example.jobmatch.domain.entity.JobApplicationEntity;
import com.example.jobmatch.domain.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobApplicationRepo extends JpaRepository<JobApplicationEntity, Integer> {
    @Query(value = "select u.* from USER u inner join JOB_APPLICATION ja on u.USER_ID = ja.USER_ID where ja.JOB_ID = :jobId",nativeQuery = true)
    List<UserEntity> getUserHaventApply(@Param("jobId")Integer jobId);
}
