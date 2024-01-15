package com.example.jobmatch.domain.jobImages;

import com.example.jobmatch.auth.Upload;
import com.example.jobmatch.entity.JobImageEntity;
import com.example.jobmatch.domain.job.JobsRepo;
import com.example.jobmatch.respon.Respon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class JobImageService {
    @Autowired
    private JobImagesRepo jobImagesRepo;
    @Autowired
    private JobsRepo jobsRepo;
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

    public Respon createImages(JobImagesRequest jobImagesRequest) {
        try {
            this.root = Paths.get(isWindows() ? UPLOAD_WIN : UPLOAD_LINUX);
            for (MultipartFile files : jobImagesRequest.getFile()) {
                String newNameFile = upload.createImages(files, this.root.toString());
                JobImageEntity jobImageEntity = new JobImageEntity();
                jobImageEntity.setLink(host + newNameFile);
                jobImageEntity.setJobsEntity(jobsRepo.findById(jobImagesRequest.getJobId()).get());
                jobImagesRepo.save(jobImageEntity);
            }
            return new Respon<String>("Upload thành công");
        } catch (Exception e) {
            return new Respon<String>("Upload thất bại");
        }
    }

    public Respon updateImages(Integer jobImageId, JobImagesRequest jobImagesRequest) {
        try {
            this.root = Paths.get(isWindows() ? UPLOAD_WIN : UPLOAD_LINUX);
            JobImageEntity jobImageEntity = jobImagesRepo.findById(jobImageId).get();
            for (MultipartFile files : jobImagesRequest.getFile()) {
                String newNameFile = upload.createImages(files, this.root.toString());
                jobImageEntity.setLink(host + newNameFile);
                jobImagesRepo.save(jobImageEntity);
            }
            return new Respon<String>("Update image job thành công");
        } catch (Exception e) {
            return new Respon<String>("Update image job thất bại");
        }
    }

    @Transactional
    public Respon deleteImages(Integer jobImageId) {
        try {
            jobImagesRepo.deleteByJobImageId(jobImageId);
            return new Respon<String>("Delete image job thành công");
        } catch (Exception e) {
            return new Respon<String>("Delete image job thất bại");
        }
    }
}