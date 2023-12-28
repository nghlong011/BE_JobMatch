package com.example.jobmatch.domain.companyImages;

import com.example.jobmatch.domain.entity.CompanyImageEntity;
import com.example.jobmatch.domain.user.UserRepo;
import com.example.jobmatch.respon.Respon;
import jakarta.annotation.PostConstruct;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.UUID;

@Component
public class CompanyImageService {
    @Autowired
    private CompanyImagesRepo companyImagesRepo;
    @Autowired
    private UserRepo userRepo;
    private Path root;

    private final static String UPLOAD_WIN = "uploads/companyImages";
    private final static String UPLOAD_LINUX = "/uploads/companyImages/";

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
                String fileName = files.getOriginalFilename();
                String extension = FilenameUtils.getExtension(fileName);
                String newNameFile = (UUID.randomUUID()) + "." + extension;
                Path target = Paths.get(this.root.toString() + "/" + newNameFile);

                Files.copy(files.getInputStream(), Paths.get(this.root.toString() + "/" + fileName), StandardCopyOption.REPLACE_EXISTING);
                Files.move(Paths.get(this.root.toString() + "/" + fileName), target);
                CompanyImageEntity companyImageEntity = new CompanyImageEntity();
                companyImageEntity.setLink(target.toString());
                companyImageEntity.setCompanyEntity(userRepo.findByEmail(principal.getName()).getCompanyEntity());
                companyImagesRepo.save(companyImageEntity);
            }
            return new Respon<String>("Upload thành công");
        } catch (IOException e) {
            return new Respon<String>("Upload thất bại");
        }
    }
}
