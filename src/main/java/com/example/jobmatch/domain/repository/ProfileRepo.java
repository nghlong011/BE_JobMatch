package com.example.jobmatch.domain.repository;

import com.example.jobmatch.domain.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepo extends JpaRepository<ProfileEntity, Integer> {
}
