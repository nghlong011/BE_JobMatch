package com.example.jobmatch.domain.user.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class UserResponse {
    private Integer userId;
    private String email;
    private String name;
    private String phone;
    private String address;
    private Boolean gender;
    private Date dob;
    private String avatar;
}
