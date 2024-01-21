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

    @Query(value = "SELECT u.* FROM USER u JOIN JOBS_APPLICATION ja ON u.USER_ID = ja.USER_ID WHERE ja.JOB_ID = ?", nativeQuery = true)
    List<UserEntity> findUserByJobId(Integer jobId);

    @Query(value = "SELECT u.* FROM USER u JOIN ROLE r on u.ROLE_ID = r.ROLE_ID AND u.ROLE_ID IN (2,1)", nativeQuery = true)
    List<UserEntity> findAllUser();
}
