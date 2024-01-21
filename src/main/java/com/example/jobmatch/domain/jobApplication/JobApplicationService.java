package com.example.jobmatch.domain.jobApplication;

import com.example.jobmatch.auth.Upload;
import com.example.jobmatch.entity.JobApplicationEntity;
import com.example.jobmatch.entity.JobsEntity;
import com.example.jobmatch.entity.UserEntity;
import com.example.jobmatch.domain.job.JobsRepo;
import com.example.jobmatch.domain.jobApplication.dto.JobApplicationRequest;
import com.example.jobmatch.domain.user.UserRepo;
import com.example.jobmatch.domain.user.response.UserResponse;
import com.example.jobmatch.respon.Respon;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JobApplicationService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JobsRepo jobsRepo;
    @Autowired
    private JobAppRepo jobAppRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private Upload upload;
    private Path root;
    private final static String UPLOAD_WIN = "uploads/cv";
    private final static String UPLOAD_LINUX = "/opt/uploads/cv";
    @Value("${host.cv}")
    private String host;

    private boolean isWindows() {
        String os = System.getProperty("os.name");
        return os.startsWith("Windows");
    }

    private JobApplicationService() {
        root = Paths.get(isWindows() ? UPLOAD_WIN : UPLOAD_LINUX);
    }

    @PostConstruct
    public void init() {
        try {
            boolean isDirectory = Files.isDirectory(this.root);
            if (!isDirectory) {
                Files.createDirectories(root);
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    public Respon create(Principal principal, JobApplicationRequest jobApplicationRequest) {
        try {
            UserEntity userEntity = userRepo.findByEmail(principal.getName());
            JobsEntity jobsEntity = jobsRepo.findById(jobApplicationRequest.getJobId()).get();

            if (!jobAppRepo.existsByJobsEntityAndAndUserEntity(jobsEntity, userEntity)) {
                JobApplicationEntity jobApplicationEntity = new JobApplicationEntity();
                modelMapper.map(jobApplicationRequest, jobApplicationEntity);
                String newNameFile = upload.createImages(jobApplicationRequest.getContent(), this.root.toString());
                jobApplicationEntity.setContent(host + newNameFile);
                jobApplicationEntity.setUserEntity(userEntity);
                jobApplicationEntity.setJobsEntity(jobsEntity);
                List<JobApplicationEntity> jobApplicationEntityList = new ArrayList<>();
                jobApplicationEntityList.add(jobApplicationEntity);
                userEntity.setJobApplicationEntities(jobApplicationEntityList);
                jobsRepo.save(jobsEntity);
                userRepo.save(userEntity);
                return new Respon<>("Ứng tuyển thành công");
            }
            return new Respon<>("Ứng tuyển thất bại");
        } catch (Exception e) {
            return new Respon<>("Ứng tuyển thất bại: ", e);
        }
    }

    public Respon getUserHaventApply(Integer jobId) {
        try {
            List<UserEntity> listUser = userRepo.findUserByJobId(jobId);
            List<UserResponse> response = new ArrayList<>();
            for (UserEntity userEntity : listUser) {
                UserResponse entity = new UserResponse(userEntity.getUserId(), userEntity.getEmail(), userEntity.getName(), userEntity.getPhone(),
                        userEntity.getAddress(), userEntity.getGender(), userEntity.getDob(), userEntity.getAvatar());
                response.add(entity);
            }
            return new Respon<>(String.format("Lấy danh sách ứng viên đã nộp đơn cho jobId = %s thành công", jobId), response);
        } catch (Exception e) {
            return new Respon<>("Lấy danh sách thất bại: " + e);
        }
    }

    public Respon update(JobApplicationRequest jobApplicationRequest) {
        try {
            JobApplicationEntity applicationEntity = jobAppRepo.findById(jobApplicationRequest.getJobAppId()).get();
            JobsEntity jobsEntity = applicationEntity.getJobsEntity();
            Integer countStatusIs2 = jobAppRepo.countAllByStatusAndAndJobsEntity(2,jobsEntity);
            if (countStatusIs2 <= jobsEntity.getNumberRecruit()) {
                applicationEntity.setStatus(jobApplicationRequest.getStatus());
                jobAppRepo.save(applicationEntity);
                return new Respon<>("Cập nhật trạng thái thành công");
            }
            return new Respon<>("Cập nhật trạng thái thất bại");
        } catch (Exception e) {
            return new Respon<>("Cập nhật trạng thái thất bại");
        }
    }

}
