package com.example.jobmatch.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "COMPANY_IMAGES")
public class CompanyImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMPANY_IMAGE_ID")
    private Integer companyImageId;

    @Column(name = "LINK")
    private String link;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "COMPANY_ID")
    private CompanyEntity companyEntity;
}
