package com.example.jobmatch.domain.skill;

import com.example.jobmatch.domain.entity.SkillEntity;
import com.example.jobmatch.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepo extends JpaRepository<SkillEntity, Integer> {
    @Query(value = "select sk.* from skill sk where sk.user_id = :idUser", nativeQuery = true)
    SkillEntity getSkillFromUserId(@Param("idUser") Integer idUser);
}
