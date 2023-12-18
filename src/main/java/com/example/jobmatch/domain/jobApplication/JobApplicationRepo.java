package com.example.jobmatch.domain.jobApplication;

import com.example.jobmatch.domain.entity.JobApplicationEntity;
import com.example.jobmatch.domain.entity.UserEntity;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobApplicationRepo extends JpaRepository<JobApplicationEntity, Integer> {
//    @Query(value = "select u.* from USER u inner join JOB_APPLICATION ja on u.USER_ID = ja.USER_ID where ja.JOB_ID = :jobId", nativeQuery = true)
//    @SuppressWarnings("unchecked")
//    @org.hibernate.annotations.QueryHints(value = { @QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true") })
//    List<UserEntity> getUserHaventApply(@Param("jobId")Integer jobId);
}
//select new com.example.jobmatch.domain.entity.UserEntity(u.USER_ID, u.EMAIL, u.NAME, u.PHONE, u.ADDRESS, u.GENDER, u.DOB, u.AVATAR ) from USER u inner join JOB_APPLICATION ja on u.USER_ID = ja.USER_ID where ja.JOB_ID = ?