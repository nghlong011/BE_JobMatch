package com.example.jobmatch.domain.user.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class RegisterUserRequest {
    private Integer userId;
    private String email;
    private String password;
    private String name;
    private String phone;
    private String address;
    private Boolean gender;
    private String activeCode;
    private Date dob;
    private String role;
}
