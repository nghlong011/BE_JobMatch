package com.example.jobmatch.seeder;

import com.example.jobmatch.domain.repository.RoleRepo;
import com.example.jobmatch.seeder.Enum.RoleEnum;
import com.example.jobmatch.domain.entity.RoleEntity;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleSeeder {
    @Autowired
    RoleRepo roleRepo;
    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    public void run() {
        for (RoleEnum roleEnum : RoleEnum.values()) {
            if (!roleRepo.existsByRoleName(roleEnum.getRoleName())) {
                RoleEntity roleEntity = modelMapper.map(roleEnum.getRoleName(), RoleEntity.class);
                roleEntity.setRoleName(roleEnum.getRoleName());
                roleRepo.save(roleEntity);
            }
        }
    }
}
