package com.example.jobmatch.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.*;

@Entity
@Getter
@Setter
@Table(name = "JOBS")
public class JobsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "JOB_ID")
    private Integer jobId;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.PERSIST)
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

    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    @Column(name = "ANNOUCEMENT_DATE", updatable = false)
    private Date annoucementDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "EXPIRATION_DATE")
    private Date expirationDate;

    @Column(name = "WORK_EXPERIENCE")
    private String workExperience;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "JOBS_CATEGORY",
            joinColumns = @JoinColumn(name = "JOB_ID"),
            inverseJoinColumns = @JoinColumn(name = "CATEGORY_ID")
    )
    private List<CategoryEntity> categoryEntities = new ArrayList<>();

    @OneToMany(mappedBy = "jobsEntity", cascade = CascadeType.ALL)
    private List<JobApplicationEntity> jobApplicationEntities = new ArrayList<>();

    @OneToMany(mappedBy = "jobsEntity", cascade = CascadeType.ALL)
    private List<JobImageEntity> jobImageEntities = new ArrayList<>();

}
