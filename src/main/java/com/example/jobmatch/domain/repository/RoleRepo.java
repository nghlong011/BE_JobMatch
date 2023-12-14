package com.example.jobmatch.domain.repository;

import com.example.jobmatch.domain.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<RoleEntity, Integer> {
        Boolean existsByRoleName(String roleName);

        RoleEntity findByRoleName(String roleName);
}
