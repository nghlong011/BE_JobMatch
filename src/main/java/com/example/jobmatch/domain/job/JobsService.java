package com.example.jobmatch.domain.job;

import com.example.jobmatch.auth.Upload;
import com.example.jobmatch.domain.category.CategoryRepository;
import com.example.jobmatch.domain.company.CompanyRepo;
import com.example.jobmatch.domain.company.dto.response.CompanyResponse;
import com.example.jobmatch.domain.company.dto.response.Company_JobResponse;
import com.example.jobmatch.entity.*;
import com.example.jobmatch.domain.job.dto.request.CreateJobsRequest;
import com.example.jobmatch.domain.user.UserRepo;
import com.example.jobmatch.respon.Respon;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private Upload upload;
    private Path root;
    @Value("${host.job}")
    private String host;

    private final static String UPLOAD_WIN = "uploads/jobImages";
    private final static String UPLOAD_LINUX = "/opt/uploads/jobImages";

    private boolean isWindows() {
        String os = System.getProperty("os.name");
        return os.startsWith("Windows");
    }

    public Respon createJob(Principal principal, CreateJobsRequest request) {
        try {
            this.root = Paths.get(isWindows() ? UPLOAD_WIN : UPLOAD_LINUX);
            JobsEntity jobsEntity = modelMapper.map(request, JobsEntity.class);
            CompanyEntity companyEntity;
            companyEntity = userRepo.findByEmail(principal.getName()).getCompanyEntity();
            if (companyEntity == null) {
                return new Respon<>("Đăng tin không thành công");
            }
            companyEntity = userRepo.findByEmail(principal.getName()).getCompanyEntity();
            jobsEntity.setCompanyEntity(companyEntity);
            List<CategoryEntity> categoryEntityList = new ArrayList<>();
            if (request.getCategoryId() != null) {
                CategoryEntity categoryEntity = categoryRepository.findById(request.getCategoryId()).get();
                if (categoryEntity != null) {
                    List<JobsEntity> jobsEntityList = new ArrayList<>();
                    jobsEntityList.add(jobsEntity);
                    categoryEntity.setJobsEntities(jobsEntityList);
                    categoryEntityList.add(categoryEntity);
                    jobsEntity.setCategoryEntities(categoryEntityList);
                }
            }
            if (request.getDescription() != null) {
                String newNameFileDescription = upload.createImages(request.getDescription(), root.toString());
                jobsEntity.setDescription(host + newNameFileDescription);
            } else {
                jobsEntity.setDescription(null);
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
            return new Respon<>("Xoá job thất bại ", e);
        }
    }

    public Respon updateJob(Integer jobId, CreateJobsRequest request) {
        try {
            this.root = Paths.get(isWindows() ? UPLOAD_WIN : UPLOAD_LINUX);
            JobsEntity jobsEntity = jobsRepo.findById(jobId).get();
            modelMapper.map(request, jobsEntity);
            jobsRepo.save(jobsEntity);
            return new Respon<>("Chỉnh sửa job thành công");
        } catch (Exception e) {
            return new Respon<>("Chỉnh sửa job thất bại", e);
        }
    }

    public Respon getListJob(Principal principal) {
        try {
            List<JobsEntity> listJob = userRepo.findByEmail(principal.getName()).getCompanyEntity().getJobsEntity();
            return new Respon<>("Lấy thông tin job thành công", listJob);
        } catch (Exception e) {
            return new Respon<>("Lấy thông tin job thất bại", e);
        }
    }

    public Respon getByTitle(String title) {
        try {
            List<JobsEntity> listJob = jobsRepo.findByTitle(title);
            listJob.get(0).getCompanyEntity();
            return new Respon<>("Lấy thông tin job thành công", listJob);
        } catch (Exception e) {
            return new Respon<>("Lấy thông tin job thất bại", e);
        }
    }


    public Respon getListJob() {
        try {
            List<CompanyEntity> companies = companyRepo.findAll();
            List<CompanyResponse> companyDTOs = new ArrayList<>();

            for (CompanyEntity company : companies) {
                CompanyResponse companyDTO = new CompanyResponse();
                companyDTO.setCompanyId(company.getCompanyId());
                companyDTO.setCompanyName(company.getCompanyName());
                companyDTO.setDescription(company.getDescription());
                companyDTO.setLocation(company.getLocation());
                companyDTO.setLogo(company.getLogo());

                List<String> companyImageLinks = company.getCompanyImageEntityList()
                        .stream()
                        .map(CompanyImageEntity::getLink)
                        .collect(Collectors.toList());

                companyDTO.setCompanyImage(companyImageLinks);

                List<Company_JobResponse> jobDTOs = company.getJobsEntity()
                        .stream()
                        .map(this::convertToJobDTO)
                        .collect(Collectors.toList());

                companyDTO.setJobs(jobDTOs);

                companyDTOs.add(companyDTO);
            }
            return new Respon<>("List company", companyDTOs);
        } catch (Exception e) {
            return new Respon<>("Thất bại", e);
        }
    }


    private Company_JobResponse convertToJobDTO(JobsEntity job) {
        try {
            Company_JobResponse jobDTO = new Company_JobResponse();
            jobDTO.setJobId(job.getJobId());
            jobDTO.setTitle(job.getTitle());
            jobDTO.setDescription(job.getDescription());
            jobDTO.setSalary(job.getSalary());
            jobDTO.setLocation(job.getLocation());
            jobDTO.setAnnoucementDate(job.getAnnoucementDate());
            jobDTO.setExpirationDate(job.getExpirationDate());
            jobDTO.setWorkExperience(job.getWorkExperience());

            List<String> jobImageLinks = job.getJobImageEntities()
                    .stream()
                    .map(JobImageEntity::getLink)
                    .collect(Collectors.toList());

            jobDTO.setJobsImages(jobImageLinks);

            return new Respon<>("List company", jobDTO).getData();
        } catch (Exception e) {
            return null;
        }
    }

    public Respon getJobApplyByUser(Principal principal) {
        try {
            List<JobsEntity> listUser = jobsRepo.getJobApplyByUser(principal.getName());
            return new Respon<>("Lấy danh sách job đã ứng tuyển thành công", listUser);
        } catch (Exception e) {
            return new Respon<>("Lấy danh sách job đã ứng tuyển thất bại: " + e);
        }
    }
}
