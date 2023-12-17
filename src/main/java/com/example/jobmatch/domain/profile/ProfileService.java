package com.example.jobmatch.domain.profile;

import com.example.jobmatch.domain.company.dto.request.CompanyRequest;
import com.example.jobmatch.domain.entity.CompanyEntity;
import com.example.jobmatch.domain.entity.ProfileEntity;
import com.example.jobmatch.domain.entity.UserEntity;
import com.example.jobmatch.domain.profile.dto.request.ProfileRequest;
import com.example.jobmatch.domain.user.UserRepo;
import com.example.jobmatch.respon.Respon;
import com.example.jobmatch.seeder.RegisterSeeder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component
public class ProfileService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ProfileRepo profileRepo;
    @Autowired
    private ModelMapper modelMapper;

    public Respon create(Principal principal, ProfileRequest request) {
        try {
            ProfileEntity profileEntity = modelMapper.map(request, ProfileEntity.class);
            UserEntity userEntity;
            if (principal == null) {
                userEntity = userRepo.findByEmail(RegisterSeeder.email);
                userEntity.setProfileEntity(profileEntity);
            } else {
                userEntity = userRepo.findByEmail(principal.getName());
                userEntity.setProfileEntity(profileEntity);
            }
            profileEntity.setUserEntity(userEntity);
            userRepo.save(userEntity);
            return new Respon<>("Tạo profile thành công");
        } catch (Exception e) {
            return new Respon<>("Tạo profile thất bại ");
        }
    }

    public Respon update(Principal principal, ProfileRequest request) {
        try {
            ProfileEntity profileEntity;
            if (principal == null) {
                profileEntity = userRepo.findByEmail(RegisterSeeder.email).getProfileEntity();
            }else {
                profileEntity = userRepo.findByEmail(principal.getName()).getProfileEntity();
            }
            modelMapper.map(request, profileEntity);
            profileRepo.save(profileEntity);
            return new Respon<>("Chỉnh sửa thông tin profile thành công");
        } catch (Exception e) {
            return new Respon<>("Chỉnh sửa thông tin profile thất bại");
        }
    }
}
