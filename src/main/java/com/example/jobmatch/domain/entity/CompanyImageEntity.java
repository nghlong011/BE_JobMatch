package com.example.jobmatch.domain.entity;

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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "COMPANY_ID")
    private CompanyEntity companyEntity;
}
