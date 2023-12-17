package com.example.jobmatch.domain.company;

import com.example.jobmatch.domain.company.dto.request.CompanyRequest;
import com.example.jobmatch.domain.entity.CompanyEntity;
import com.example.jobmatch.domain.entity.UserEntity;
import com.example.jobmatch.domain.user.UserRepo;
import com.example.jobmatch.respon.Respon;
import com.example.jobmatch.seeder.RegisterSeeder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.List;

@Component
public class CompanyService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CompanyRepo companyRepo;
    @Autowired
    private UserRepo userRepo;

    public Respon getAll() {
        try {
            List<CompanyEntity> companyEntities = companyRepo.findAll();
            return new Respon<>("List copany", companyEntities);
        } catch (Exception e) {
            return new Respon<>("Thất bại");
        }
    }

    public Respon create(Principal principal, CompanyRequest request) {
        try {
            CompanyEntity companyEntity = modelMapper.map(request, CompanyEntity.class);
            UserEntity userEntity;
            if (principal == null) {
                userEntity = userRepo.findByEmail(RegisterSeeder.email);
                userEntity.setCompanyEntity(companyEntity);
            } else {
                userEntity = userRepo.findByEmail(principal.getName());
                userEntity.setCompanyEntity(companyEntity);
            }
            userRepo.save(userEntity);
            return new Respon<>("Tạo công ty thành công");
        } catch (Exception e) {
            return new Respon<>("Tạo công ty thất bại");
        }
    }

    public Respon update(Principal principal, CompanyRequest request) {
        try {
            CompanyEntity companyEntity;
            if (principal == null) {
                companyEntity = userRepo.findByEmail(RegisterSeeder.email).getCompanyEntity();
            }else {
                companyEntity = userRepo.findByEmail(principal.getName()).getCompanyEntity();
            }
            modelMapper.map(request, companyEntity);
            companyRepo.save(companyEntity);
            return new Respon<>("Chỉnh sửa thông tin công ty thành công");
        } catch (Exception e) {
            return new Respon<>("Chỉnh sửa thông tin công ty thất bại");
        }
    }
}
