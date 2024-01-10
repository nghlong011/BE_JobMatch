package com.example.jobmatch.domain.company;

import com.example.jobmatch.auth.Upload;
import com.example.jobmatch.domain.company.dto.request.CompanyRequest;
import com.example.jobmatch.domain.company.dto.response.CompanyResponse;
import com.example.jobmatch.domain.company.dto.response.Company_JobResponse;
import com.example.jobmatch.domain.user.UserRepo;
import com.example.jobmatch.entity.*;
import com.example.jobmatch.respon.Respon;
import com.example.jobmatch.seeder.RegisterSeeder;
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
import java.util.stream.Collectors;

@Component
public class CompanyService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CompanyRepo companyRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private Upload upload;
    private Path root;
    private final static String UPLOAD_WIN = "uploads/companyImages";
    private final static String UPLOAD_LINUX = "/opt/uploads/companyImages";
    @Value("${host.company}")
    private String host;

    private boolean isWindows() {
        String os = System.getProperty("os.name");
        return os.startsWith("Windows");
    }

    private CompanyService() {
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

    public Respon getAll() {
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

    public Respon create(Principal principal, CompanyRequest request) {
        try {
            CompanyEntity companyEntity = modelMapper.map(request, CompanyEntity.class);
            UserEntity userEntity;
            if (principal == null) {
                userEntity = userRepo.findByEmail(RegisterSeeder.email);
                userEntity.setCompanyEntity(companyEntity);
            } else {
                if (!request.getLogo().isEmpty()){
                    String newNameFile = upload.createImages(request.getLogo(), this.root.toString());
                    companyEntity.setLogo(host + newNameFile);
                }else {
                    companyEntity.setLogo(null);
                }
                if (!request.getDescription().isEmpty()){
                    String newNameFileDescription = upload.createImages(request.getDescription(), this.root.toString());
                    companyEntity.setDescription(host + newNameFileDescription);
                }else {
                    companyEntity.setDescription(null);
                }
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
            } else {
                companyEntity = userRepo.findByEmail(principal.getName()).getCompanyEntity();
            }
            modelMapper.map(request, companyEntity);
            if (!request.getLogo().isEmpty()){
                String newNameFile = upload.createImages(request.getLogo(), this.root.toString());
                companyEntity.setLogo(host + newNameFile);
            }else {
                companyEntity.setLogo(null);
            }
            if (!request.getDescription().getResource().exists()){
                String newNameFileDescription = upload.createImages(request.getDescription(), this.root.toString());
                companyEntity.setDescription(host + newNameFileDescription);
            }else {
                companyEntity.setDescription(null);
            }
            companyRepo.save(companyEntity);
            return new Respon<>("Chỉnh sửa thông tin công ty thành công");
        } catch (Exception e) {
            return new Respon<>("Chỉnh sửa thông tin công ty thất bại");
        }
    }


    public Respon getByTitle(String title) {
        try {
            List<CompanyEntity> companies = companyRepo.findByTitle(title);
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
}
