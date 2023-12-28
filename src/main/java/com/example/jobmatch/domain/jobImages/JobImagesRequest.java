package com.example.jobmatch.domain.jobImages;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class JobImagesRequest {
    private Integer jobId;
    private MultipartFile[] file;
}
