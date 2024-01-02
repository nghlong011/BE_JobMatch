package com.example.jobmatch.domain.user;

import com.example.jobmatch.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Integer> {
    UserEntity findByEmail(String email);
    Boolean existsByEmail(String email);

    @Query(value = "SELECT u.*\n" +
            "FROM USER u\n" +
            "JOIN JOBS_APPLICATION ja ON u.USER_ID = ja.USER_ID\n" +
            "WHERE ja.JOB_ID = ?;\n", nativeQuery = true)
    List<UserEntity> findUserByJobId(Integer jobId);


    @Query(value = "SELECT u.*\n" +
            "FROM USER u\n" +
            "WHERE u.USER_ID NOT IN (\n" +
            "    SELECT ja.USER_ID\n" +
            "    FROM JOBS_APPLICATION ja\n" +
            "    INNER JOIN JOBS j ON ja.JOB_ID = j.JOB_ID\n" +
            "    INNER JOIN USER ON USER.COMPANY_ID = j.COMPANY_ID\n" +
            "    WHERE USER.USER_ID = ?\n" +
            ")\n" +
            "AND u.COMPANY_ID IS NULL", nativeQuery = true)
    List<UserEntity> findNonApplicants(Integer userId);
}
