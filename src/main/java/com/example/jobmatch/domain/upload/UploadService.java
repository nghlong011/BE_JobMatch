package com.example.jobmatch.domain.upload;
import com.amazonaws.Response;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.jobmatch.respon.Respon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class UploadService {

    @Value("${amazonProperties.bucketName}")
    private String bucketName;

    @Value("${amazonProperties.endpointUrl}")
    private String endpointUrl;

    @Autowired
    private AmazonS3 s3Client;

    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            log.error("Error converting multipartFile to file", e);
        }
        return convertedFile;
    }
    public Respon<String> uploadFile(MultipartFile file) {
        File fileObj = convertMultiPartFileToFile(file);
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
        fileObj.delete();
        return new Respon<String>("Success", endpointUrl + "/" + bucketName + "/" + fileName.replaceAll(" ",""));
    }

    public Respon<?> deleteFile(String fileName) {
        s3Client.deleteObject(bucketName, fileName);
        return new Respon<>("Success");
    }

    public Respon<?> createBucket(String name){
        if (s3Client.doesBucketExistV2(name)) {
            return new Respon<>("This bucket already exist");
        } else {
            try {
                s3Client.createBucket(name);
                return new Respon<>("Create bucket success");
            } catch (AmazonS3Exception e) {
                return new Respon<>("Cannot create bucket");
            }
        }
    }

    public Respon<?> getListBucket(){
        List<Bucket> buckets = s3Client.listBuckets();
        return new Respon<List<Bucket>>("Get list bucket success");
    }

}