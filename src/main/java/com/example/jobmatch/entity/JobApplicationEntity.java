package com.example.jobmatch.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "JOBS_APPLICATION")
public class JobApplicationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "JOBS_APPLICATION_ID")
    private Integer jobAppId;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    @JsonIgnore
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "JOB_ID")
    @JsonIgnore
    private JobsEntity jobsEntity;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "NAME")
    private String name;
}
