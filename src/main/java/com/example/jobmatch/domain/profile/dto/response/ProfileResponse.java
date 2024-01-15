package com.example.jobmatch.domain.profile.dto.response;


import com.example.jobmatch.entity.ProfileEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ProfileResponse {
    private String email;
    private String name;
    private String phone;
    private String address;
    private Boolean gender;
    private Date dob;
    private String avatar;
    private ProfileEntity profileEntity;
}
