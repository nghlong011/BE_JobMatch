package com.example.jobmatch.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "JOB_IMAGES")
public class JobImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "JOB_IMAGE_ID")
    private Integer jobImageId;

    @Column(name = "LINK")
    private String link;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "JOB_ID")
    private JobsEntity jobsEntity;
}
