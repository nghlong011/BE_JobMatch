package com.example.jobmatch.domain.profile.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Getter
@Setter
public class ProfileRequest {
    private String education;
    private String workExperience;
    private String description;
    private String language;
    private String appreciation;
    private String resume;
    private MultipartFile avatar;
    private String name;
    private String phone;
    private String address;
    private Boolean gender;
    private Date dob;
    private String email;
}
