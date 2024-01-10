package com.example.jobmatch.seeder;

import com.example.jobmatch.domain.company.CompanyRepo;
import com.example.jobmatch.domain.job.JobsRepo;
import com.example.jobmatch.domain.profile.ProfileRepo;
import com.example.jobmatch.domain.user.RoleRepo;
import com.example.jobmatch.domain.user.UserRepo;
import com.example.jobmatch.entity.CompanyEntity;
import com.example.jobmatch.entity.RoleEntity;
import com.example.jobmatch.entity.UserEntity;
import com.example.jobmatch.seeder.Enum.RoleEnum;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class RegisterSeeder {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepo roleRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    JobsRepo jobsRepo;
    @Autowired
    ProfileRepo profileRepo;
    @Autowired
    CompanyRepo companyRepo;
    public static String email = "admin";
    public static String company = "COMPANY_TEST";

    @PostConstruct
    public void run() {
        try {
            for (RoleEnum roleEnum : RoleEnum.values()) {
                if (!roleRepo.existsByRoleName(roleEnum.getRoleName())) {
                    RoleEntity roleEntity = new RoleEntity();
                    roleEntity.setRoleName(roleEnum.getRoleName());
                    roleRepo.save(roleEntity);
                }
            }
            CompanyEntity companyEntity = new CompanyEntity();
            if (!companyRepo.existsByCompanyName(company)) {
                companyEntity.setCompanyName(company);
            }
            UserEntity userEntity = new UserEntity();
            if (!userRepo.existsByEmail(email)) {
                userEntity.setEmail(email);
                userEntity.setRoleEntity(roleRepo.findByRoleName(RoleEnum.ADMIN.getRoleName()));
                userEntity.setPassword(passwordEncoder.encode("admin"));
                userEntity.setCompanyEntity(companyEntity);
                userRepo.save(userEntity);
            }
        }catch (Exception e){
            System.out.println("exception: " + e);
        }
    }
}
