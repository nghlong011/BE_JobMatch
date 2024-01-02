package com.example.jobmatch.domain.skill;

import com.example.jobmatch.entity.SkillEntity;
import com.example.jobmatch.entity.UserEntity;
import com.example.jobmatch.domain.user.UserRepo;
import com.example.jobmatch.respon.Respon;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component
public class SkillService {
    @Autowired
    private SkillRepo skillRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;
    public Respon createSkill(Principal principal, SkillRequest skillRequest) {
        try {
            UserEntity userEntity = userRepo.findByEmail(principal.getName());
            SkillEntity skillEntity = new SkillEntity();
            modelMapper.map(skillRequest, skillEntity);
            skillEntity.setProfileEntity(userEntity.getProfileEntity());
            return new Respon<>("Tạo skill thành công");
        } catch (Exception e) {
            return new Respon<>("Tạo skill thất bại");
        }
    }

    public Respon deleteSkill(Integer skillId) {
        try {
            skillRepo.deleteById(skillId);
            return new Respon<>("Xoá skill thành công");
        } catch (Exception e) {
            return new Respon<>("Xoá skill thất bại " + e);
        }
    }

    public Respon updateSkill(Integer skillId,SkillRequest skillRequest) {
        try {
            SkillEntity skillEntity = skillRepo.findById(skillId).get();
            modelMapper.map(skillRequest, skillEntity);
            skillRepo.save(skillEntity);
            return new Respon<>("Update skill thành công");
        } catch (Exception e) {
            return new Respon<>("Update skill thất bại " + e);
        }
    }


    public Respon getSkill(Principal principal) {
        try {
            UserEntity userEntity = userRepo.findByEmail(principal.getName());
            SkillEntity skillEntity = skillRepo.getSkillFromUserId(userEntity.getUserId());
            return new Respon<>("Lấy thông tin skill thành công", skillEntity);
        } catch (Exception e) {
            return new Respon<>("Lấy thông tin skill thất bại");
        }
    }
}
