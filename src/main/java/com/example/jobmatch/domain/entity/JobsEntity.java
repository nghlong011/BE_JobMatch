package com.example.jobmatch.domain.entity;

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
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
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
    @Column(name = "ANNOUCEMENT_DATE", updatable = false)
    private Date annoucementDate;

    @Column(name = "EXPIRATION_DATE")
    private Date expirationDate;

    @Column(name = "WORK_EXPERIENCE")
    private String workExperience;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "JOBS_CATEGORY",
            joinColumns = @JoinColumn(name = "JOB_ID"),
            inverseJoinColumns = @JoinColumn(name = "CATEGORY_ID")
    )
    private Set<CategoryEntity> categoryEntities = new HashSet<>();

    @OneToMany(mappedBy = "jobsEntity",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<JobApplicationEntity> jobApplicationEntities = new ArrayList<>();
}
