package com.example.jobmatch.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "JOBS")
public class JobsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "JOB_ID")
    private Integer jobId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID")
    private UserEntity users;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "COMPANY_ID")
    private CompanyEntity companyEntity;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "SALARY")
    private String salary;

    @Column(name = "LOCATION")
    private String location;

    @Column(name = "ANNOUCEMENT_DATE")
    private Date annoucementDate;

    @Column(name = "EXPIRATION_DATE")
    private Date expirationDate;

    @Column(name = "WORK_EXPERIENCE")
    private String workExperience;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "JOBS-CATEGORY",
            joinColumns = @JoinColumn(name = "JOB_ID"),
            inverseJoinColumns = @JoinColumn(name = "CATEGORY_ID")
    )
    private Set<CategoryEntity> categoryEntities = new HashSet<>();
}
