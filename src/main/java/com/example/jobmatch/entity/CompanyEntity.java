package com.example.jobmatch.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "COMPANY")
public class CompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMPANY_ID")
    private Integer companyId;

    @Column(name = "COMPANY_NAME")
    private String companyName;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "LOCATION")
    private String location;

    @Column(name = "LOGO")
    private String logo;

    @OneToMany(mappedBy = "companyEntity", cascade = CascadeType.ALL)
    private List<JobsEntity> JobsEntity = new ArrayList<>();

    @OneToMany(mappedBy = "companyEntity", cascade = CascadeType.ALL)
    private List<CompanyImageEntity> companyImageEntityList = new ArrayList<>();
}
