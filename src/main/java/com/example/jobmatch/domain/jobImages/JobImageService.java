package com.example.jobmatch.domain.jobImages;

import com.example.jobmatch.domain.entity.JobImageEntity;
import com.example.jobmatch.domain.job.JobsRepo;
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
import java.util.UUID;

@Component
public class JobImageService {
    @Autowired
    private JobImagesRepo jobImagesRepo;
    @Autowired
    private JobsRepo jobsRepo;
    private Path root;

    private final static String UPLOAD_WIN = "uploads/jobImages/";
    private final static String UPLOAD_LINUX = "/uploads/jobImages/";

    private boolean isWindows() {
        String os = System.getProperty("os.name");
        return os.startsWith("Windows");
    }

    private JobImageService() {
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

    public Respon createImages(JobImagesRequest jobImagesRequest) {
        try {
            for (MultipartFile files : jobImagesRequest.getFile()) {
                String fileName = files.getOriginalFilename();
                String extension = FilenameUtils.getExtension(fileName);
                String newNameFile = (UUID.randomUUID()) + "." + extension;
                Path target = Paths.get(this.root.toString() + newNameFile);

                Files.copy(files.getInputStream(), Paths.get(this.root.toString() + fileName), StandardCopyOption.REPLACE_EXISTING);
                Files.move(Paths.get(this.root.toString() + fileName), target);
                JobImageEntity jobImageEntity = new JobImageEntity();
                jobImageEntity.setLink(target.toString());
                jobImageEntity.setJobsEntity(jobsRepo.findById(jobImagesRequest.getJobId()).get());
                jobImagesRepo.save(jobImageEntity);
            }
            return new Respon<String>("Upload thành công");
        } catch (IOException e) {
            return new Respon<String>("Upload thất bại");
        }
    }
}