package com.example.jobmatch.domain.profile;

import com.example.jobmatch.auth.Upload;
import com.example.jobmatch.domain.profile.dto.response.ProfileResponse;
import com.example.jobmatch.entity.ProfileEntity;
import com.example.jobmatch.entity.UserEntity;
import com.example.jobmatch.domain.profile.dto.request.ProfileRequest;
import com.example.jobmatch.domain.user.UserRepo;
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

@Component
public class ProfileService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ProfileRepo profileRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private Upload upload;
    private Path root;
    @Value("${host.avatar}")
    private String host;

    private final static String UPLOAD_WIN = "uploads/avatar";
    private final static String UPLOAD_LINUX = "/opt/uploads/avatar";

    private boolean isWindows() {
        String os = System.getProperty("os.name");
        return os.startsWith("Windows");
    }

    private ProfileService() {
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

    public Respon create(Principal principal, ProfileRequest request) {
        try {
            ProfileEntity profileEntity = modelMapper.map(request, ProfileEntity.class);
            UserEntity userEntity;
            if (principal == null) {
                userEntity = userRepo.findByEmail(RegisterSeeder.email);
                userEntity.setProfileEntity(profileEntity);
            } else {
                userEntity = userRepo.findByEmail(principal.getName());
                String newNameFile = upload.createImages(request.getAvatar(), this.root.toString());
                userEntity.setAvatar(host + newNameFile);
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
            UserEntity userEntity = userRepo.findByEmail(principal.getName());
            ProfileEntity profileEntity = userEntity.getProfileEntity();
            if (request.getName() != null && !request.getName().isEmpty()) {
                userEntity.setName(request.getName());
            }
            if (request.getPhone() != null && !request.getPhone().isEmpty()) {
                userEntity.setPhone(request.getPhone());
            }
            if (request.getAddress() != null && !request.getAddress().isEmpty()) {
                userEntity.setAddress(request.getAddress());
            }
            if (request.getGender() != null) {
                userEntity.setGender(request.getGender());
            }
            if (request.getDob() != null) {
                userEntity.setDob(request.getDob());
            }
            if (request.getAvatar() != null){
                String newNameFileDescription = upload.createImages(request.getAvatar(), this.root.toString());
                userEntity.setAvatar(host + newNameFileDescription);
            }if (request.getEducation() != null && !request.getEducation().isEmpty()) {
                profileEntity.setEducation(request.getEducation());
            }

            if (request.getWorkExperience() != null && !request.getWorkExperience().isEmpty()) {
                profileEntity.setWorkExperience(request.getWorkExperience());
            }

            if (request.getDescription() != null && !request.getDescription().isEmpty()) {
                profileEntity.setDescription(request.getDescription());
            }

            if (request.getLanguage() != null && !request.getLanguage().isEmpty()) {
                profileEntity.setLanguage(request.getLanguage());
            }

            if (request.getAppreciation() != null && !request.getAppreciation().isEmpty()) {
                profileEntity.setAppreciation(request.getAppreciation());
            }

            if (request.getResume() != null && !request.getResume().isEmpty()) {
                profileEntity.setResume(request.getResume());
            }

            userRepo.save(userEntity);
            profileRepo.save(profileEntity);
            return new Respon<>("Chỉnh sửa thông tin profile thành công");
        } catch (Exception e) {
            return new Respon<>("Chỉnh sửa thông tin profile thất bại");
        }
    }

    public Respon get(ProfileRequest request) {
        try {
            UserEntity userEntity = userRepo.findByEmail(request.getEmail());
            ProfileResponse profileResponse = new ProfileResponse();
            profileResponse.setEmail(userEntity.getEmail());
            profileResponse.setName(userEntity.getName());
            profileResponse.setAddress(userEntity.getAddress());
            profileResponse.setGender(userEntity.getGender());
            profileResponse.setPhone(userEntity.getPhone());
            profileResponse.setAvatar(userEntity.getAvatar());
            profileResponse.setProfileEntity(userEntity.getProfileEntity());
            return new Respon<>("Lấy thông tin thành công", profileResponse);
        } catch (Exception e) {
            return new Respon<>("Lấy thông tin thất bại");
        }
    }
}
