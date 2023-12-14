package com.example.jobmatch.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "PROFILE")
public class ProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROFILE_ID")
    private Integer profileId;

    @OneToOne(mappedBy = "profileEntity")
    @PrimaryKeyJoinColumn(name = "USER_ID")
    private UserEntity userEntity;

    @Column(name = "EDUCATION")
    private String education;

    @Column(name = "WORK_EXPERIENCE")
    private String workExperience;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "LANGUAGE")
    private String language;

    @Column(name = "APPRECIATION")
    private String appreciation;

    @Column(name = "RESUME")
    private String resume;
}
