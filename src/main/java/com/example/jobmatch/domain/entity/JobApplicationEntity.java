package com.example.jobmatch.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "JOBS_APPLICATION")
public class JobApplicationEntity {
    @EmbeddedId
    private JobApplicationId id;


//    @Id
//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "USER_ID")
//    private UserEntity user;
//
//    @Id
//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "JOB_ID")
//    private JobsEntity jobs;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "STATUS")
    private String status;
}
