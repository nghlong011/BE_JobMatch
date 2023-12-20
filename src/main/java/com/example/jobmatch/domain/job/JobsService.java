package com.example.jobmatch.domain.job;

import com.example.jobmatch.domain.company.CompanyRepo;
import com.example.jobmatch.domain.entity.JobsEntity;
import com.example.jobmatch.domain.entity.UserEntity;
import com.example.jobmatch.domain.job.dto.request.CreateJobsRequest;
import com.example.jobmatch.domain.user.UserRepo;
import com.example.jobmatch.respon.Respon;
import com.example.jobmatch.seeder.RegisterSeeder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

@Component
public class JobsService {
    @Autowired
    private JobsRepo jobsRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CompanyRepo companyRepo;
    @Autowired
    private ModelMapper modelMapper;

    public Respon createJob(Principal principal, CreateJobsRequest request) {
        try {
            JobsEntity jobsEntity = modelMapper.map(request, JobsEntity.class);
            UserEntity userEntity;
            if (principal == null) {
                userEntity = userRepo.findByEmail(RegisterSeeder.email);
                jobsEntity.setCompanyEntity(userEntity.getCompanyEntity());
//                jobsEntity.setUsersEntity(userEntity);
            } else {
                userEntity = userRepo.findByEmail(principal.getName());
//                jobsEntity.setUsersEntity(userEntity);
                jobsEntity.setCompanyEntity(userEntity.getCompanyEntity());
            }
            jobsRepo.save(jobsEntity);
            return new Respon<>("Đăng tin thành công");
        } catch (Exception e) {
            return new Respon<>("Đăng tin thất bại");
        }
    }

    @Transactional
    public Respon deleteJob(Integer jobId) {
        try {
            jobsRepo.deleteByJobId(jobId);
            return new Respon<>("Xoá job thành công");
        } catch (Exception e) {
            return new Respon<>("Xoá job thất bại " + e);
        }
    }

    public Respon updateJob(Integer jobId, CreateJobsRequest request) {
        try {
            JobsEntity jobsEntity = jobsRepo.findById(jobId).get();
            modelMapper.map(request, jobsEntity);
            jobsRepo.save(jobsEntity);
            return new Respon<>("Chỉnh sửa job thành công");
        } catch (Exception e) {
            return new Respon<>("Chỉnh sửa job thất bại");
        }
    }


    public Respon getListJob() {
        try {
            List<JobsEntity> listJob = jobsRepo.findAll();
            return new Respon<>("Lấy thông tin job thành công", listJob);
        } catch (Exception e) {
            return new Respon<>("Lấy thông tin job thất bại");
        }
    }
}
