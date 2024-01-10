package com.example.jobmatch.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "PROFILE")
public class ProfileEntity {
    @Id
    @Column(name = "USER_ID")
    private Integer profileId;

    @OneToOne
    @MapsId
    @JsonBackReference
    @JoinColumn(name = "USER_ID")
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

    @JsonManagedReference
    @OneToMany(mappedBy = "profileEntity", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<SkillEntity> skillEntities = new ArrayList<>();
}
