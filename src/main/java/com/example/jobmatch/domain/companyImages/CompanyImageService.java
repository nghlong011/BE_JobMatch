package com.example.jobmatch.domain.companyImages;

import com.example.jobmatch.auth.Upload;
import com.example.jobmatch.entity.CompanyImageEntity;
import com.example.jobmatch.domain.user.UserRepo;
import com.example.jobmatch.respon.Respon;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;

@Component
public class CompanyImageService {
    @Autowired
    private CompanyImagesRepo companyImagesRepo;
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

    private CompanyImageService() {
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

    public Respon createImages(Principal principal, CompanyImageRequest companyImageRequest) {
        try {
            for (MultipartFile files : companyImageRequest.getFile()) {
                String newNameFile = upload.createImages(files, this.root.toString());
                CompanyImageEntity companyImageEntity = new CompanyImageEntity();
                companyImageEntity.setLink(host + newNameFile);
                companyImageEntity.setCompanyEntity(userRepo.findByEmail(principal.getName()).getCompanyEntity());
                companyImagesRepo.save(companyImageEntity);
            }
            return new Respon<>("Upload thành công");
        }catch (Exception e){
            return new Respon<>("Upload thất bại", e);
        }
    }

    public Respon updateImages(Integer companyImageId, CompanyImageRequest companyImageRequest) {
        try {
            CompanyImageEntity companyImageEntity = companyImagesRepo.findById(companyImageId).get();
            for (MultipartFile files : companyImageRequest.getFile()) {
                String newNameFile = upload.createImages(files, this.root.toString());
                companyImageEntity.setLink(host + newNameFile);
                companyImagesRepo.save(companyImageEntity);
            }
            return new Respon<>("Upload thành công");
        }catch (Exception e){
            return new Respon<>("Upload thất bại", e);
        }
    }
}
