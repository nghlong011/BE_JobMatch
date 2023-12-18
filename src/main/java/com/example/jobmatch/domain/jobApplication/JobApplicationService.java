package com.example.jobmatch.domain.jobApplication;

import com.example.jobmatch.domain.company.CompanyRepo;
import com.example.jobmatch.domain.entity.JobApplicationEntity;
import com.example.jobmatch.domain.entity.JobsEntity;
import com.example.jobmatch.domain.entity.UserEntity;
import com.example.jobmatch.domain.job.JobsRepo;
import com.example.jobmatch.domain.jobApplication.dto.JobApplicationRequest;
import com.example.jobmatch.domain.user.UserRepo;
import com.example.jobmatch.respon.Respon;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.List;

@Component
public class JobApplicationService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JobsRepo jobsRepo;

    @Autowired
    private JobApplicationRepo jobApplicationRepo;

    @Autowired
    private CompanyRepo companyRepo;

    @Autowired
    private ModelMapper modelMapper;
    public Respon create(Principal principal, JobApplicationRequest jobApplicationRequest) {
        try {
            UserEntity userEntity = userRepo.findByEmail(principal.getName());
            JobsEntity jobsEntity = jobsRepo.findById(jobApplicationRequest.getJobId()).get();
            JobApplicationEntity jobApplicationEntity = new JobApplicationEntity();
            modelMapper.map(jobApplicationRequest, jobApplicationEntity);
//            jobApplicationEntity.setUser(userEntity);
//            jobApplicationEntity.setJobs(jobsEntity);
            jobApplicationRepo.save(jobApplicationEntity);
            return new Respon<>("Ứng tuyển thành công");
        } catch (Exception e) {
            return new Respon<>("Ứng tuyển thất bại");
        }
    }

    public Respon getUserHaventApply(Integer jobId, int page, int limit) {
        try {
            Pageable pageable = PageRequest.of(page, limit);
//            List<UserEntity> listUser = jobApplicationRepo.findById(jobId).get();
            return new Respon<>("Lấy danh sách thành công");
        } catch (Exception e) {
            return new Respon<>("Lấy danh sách thất bại"+ e);
        }
    }
}
