package com.example.jobmatch.domain.user;

import com.example.jobmatch.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Integer> {
    UserEntity findByEmail(String email);
    Boolean existsByEmail(String email);
}
