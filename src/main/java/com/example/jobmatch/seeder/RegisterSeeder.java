package com.example.jobmatch.seeder;

import com.example.jobmatch.domain.company.CompanyRepo;
import com.example.jobmatch.domain.entity.CompanyEntity;
import com.example.jobmatch.domain.entity.JobsEntity;
import com.example.jobmatch.domain.entity.UserEntity;
import com.example.jobmatch.domain.job.JobsRepo;
import com.example.jobmatch.domain.user.RoleRepo;
import com.example.jobmatch.domain.user.UserRepo;
import com.example.jobmatch.seeder.Enum.RoleEnum;
import com.example.jobmatch.domain.entity.RoleEntity;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegisterSeeder {
    @Autowired
    RoleRepo roleRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    JobsRepo jobsRepo;
    @Autowired
    CompanyRepo companyRepo;
    public static String email = "EMAIL_TEST";
    public static String company = "COMPANY_TEST";
    public static String jobs = "JOBS_TEST";
    @PostConstruct
    public void run() {
        for (RoleEnum roleEnum : RoleEnum.values()) {
            if (!roleRepo.existsByRoleName(roleEnum.getRoleName())) {
                RoleEntity roleEntity = new RoleEntity();
                roleEntity.setRoleName(roleEnum.getRoleName());
                roleRepo.save(roleEntity);
            }
        }
        CompanyEntity companyEntity = new CompanyEntity();
        if (!companyRepo.existsByCompanyName(company)){
            companyEntity.setCompanyName(company);
        }
        UserEntity userEntity = new UserEntity();
        if (!userRepo.existsByEmail(email)) {
            userEntity.setEmail(email);
            userEntity.setRoleEntity(roleRepo.findByRoleName(RoleEnum.ADMIN.getRoleName()));
            userEntity.setCompanyEntity(companyEntity);
        }
        JobsEntity jobsEntity = new JobsEntity();
        if (!jobsRepo.existsById(1)) {
            jobsEntity.setCompanyEntity(companyEntity);
//            jobsEntity.setUsersEntity(userEntity);
            jobsEntity.setTitle(jobs);
            jobsRepo.save(jobsEntity);
        }
    }
}
