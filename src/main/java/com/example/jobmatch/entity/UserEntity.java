package com.example.jobmatch.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Entity
@Getter
@Setter
@Table(name = "USER")
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Integer userId;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "GENDER")
    private Boolean gender;

    @Column(name = "DOB")
    private Date dob;

    @Column(name = "AVATAR")
    private String avatar;

    @Column(name = "TOKEN")
    private String token;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "COMPANY_ID", referencedColumnName = "COMPANY_ID")
    private CompanyEntity companyEntity;

    @JsonManagedReference
    @OneToOne(mappedBy = "userEntity", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private ProfileEntity profileEntity;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "ROLE_ID")
    private RoleEntity roleEntity;

    @JsonIgnore
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private List<JobApplicationEntity> jobApplicationEntities = new ArrayList<>();

    public UserEntity(Integer userId, String email, String name, String phone, String address, Boolean gender, Date dob, String avatar){
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.gender = gender;
        this.dob = dob;
        this.avatar = avatar;
    }
}
