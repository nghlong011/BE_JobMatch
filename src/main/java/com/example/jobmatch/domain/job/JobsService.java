package com.example.jobmatch.domain.job;

import com.example.jobmatch.domain.category.CategoryRepository;
import com.example.jobmatch.domain.company.CompanyRepo;
import com.example.jobmatch.domain.entity.CategoryEntity;
import com.example.jobmatch.domain.entity.CompanyEntity;
import com.example.jobmatch.domain.entity.JobsEntity;
import com.example.jobmatch.domain.job.dto.request.CreateJobsRequest;
import com.example.jobmatch.domain.user.UserRepo;
import com.example.jobmatch.respon.Respon;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class JobsService {
    @Autowired
    private JobsRepo jobsRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CompanyRepo companyRepo;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    public Respon createJob(Principal principal, CreateJobsRequest request) {
        try {
            JobsEntity jobsEntity = modelMapper.map(request, JobsEntity.class);
            CompanyEntity companyEntity;
            companyEntity = userRepo.findByEmail(principal.getName()).getCompanyEntity();
            if (companyEntity == null) {
                return new Respon<>("Đăng tin không thành công");
            }
            companyEntity = userRepo.findByEmail(principal.getName()).getCompanyEntity();
            jobsEntity.setCompanyEntity(companyEntity);
            List<CategoryEntity> categoryEntityList = new ArrayList<>();
            if (request.getCategoryId() != null){
                CategoryEntity categoryEntity = categoryRepository.findById(request.getCategoryId()).get();
                if (categoryEntity != null){
                    List<JobsEntity> jobsEntityList = new ArrayList<>();
                    jobsEntityList.add(jobsEntity);
                    categoryEntity.setJobsEntities(jobsEntityList);
                    categoryEntityList.add(categoryEntity);
                    jobsEntity.setCategoryEntities(categoryEntityList);
                }
            }
            jobsRepo.save(jobsEntity);
            return new Respon<>("Đăng tin thành công");
        } catch (Exception e) {
            return new Respon<>("Đăng tin thất bại", e);
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


    public Respon getListJob(Principal principal) {
        try {
            List<JobsEntity> listJob = userRepo.findByEmail(principal.getName()).getCompanyEntity().getJobsEntity();
            return new Respon<>("Lấy thông tin job thành công", listJob);
        } catch (Exception e) {
            return new Respon<>("Lấy thông tin job thất bại");
        }
    }
}
