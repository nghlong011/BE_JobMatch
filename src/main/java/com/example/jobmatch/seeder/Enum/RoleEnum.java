package com.example.jobmatch.seeder.Enum;

import lombok.Getter;

public enum RoleEnum {
    USER("ROLE_JOB_SEEKER"),
    EMPLOYEE("ROLE_EMPLOYER"),
    ADMIN("ROLE_ADMIN");
    @Getter
    private String roleName;

    RoleEnum(String roleName) {
        this.roleName = roleName;
    }
}
