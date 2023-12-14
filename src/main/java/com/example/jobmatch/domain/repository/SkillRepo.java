package com.example.jobmatch.domain.repository;

import com.example.jobmatch.domain.entity.SkillEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepo extends JpaRepository<SkillEntity, Integer> {
}
