package com.example.jobmatch.domain.entity;

import com.example.jobmatch.domain.entity.CategoryEntity;
import com.example.jobmatch.domain.entity.CompanyEntity;
import com.example.jobmatch.domain.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

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

    @OneToMany(mappedBy = "id.job", cascade = CascadeType.ALL)
    private Set<JobApplicationEntity> jobApplications = new HashSet<>();
}
