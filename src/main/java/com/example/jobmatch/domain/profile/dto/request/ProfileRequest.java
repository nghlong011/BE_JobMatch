package com.example.jobmatch.domain.profile.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileRequest {
    private String education;
    private String workExperience;
    private String description;
    private String language;
    private String appreciation;
    private String resume;
}
