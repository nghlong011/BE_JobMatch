package com.example.jobmatch.domain.user.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class RegisterUserRequest {
    private String email;
    private String password;
    private String name;
    private String phone;
    private String address;
    private String activeCode;
    private Boolean gender;
    private Date dob;
    private String avatar;
}
