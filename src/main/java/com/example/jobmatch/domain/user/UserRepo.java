package com.example.jobmatch.domain.user;

import com.example.jobmatch.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Integer> {
    UserEntity findByEmail(String email);
    Boolean existsByEmail(String email);

    @Query(value = "SELECT DISTINCT u.* FROM USER u \n" +
            "JOIN JOBS_APPLICATION ja ON ja.USER_ID = u.USER_ID\n" +
            "JOIN JOBS j ON j.JOB_ID = ja.JOB_ID\n" +
            "WHERE \n" +
            "u.USER_ID NOT IN (\n" +
            "SELECT USER.USER_ID FROM USER \n" +
            "    JOIN COMPANY ON COMPANY.COMPANY_ID = USER.COMPANY_ID\n" +
            ")\n" +
            "AND j.JOB_ID = ?", nativeQuery = true)
    List<UserEntity> findUserByJobId(Integer id);
}
