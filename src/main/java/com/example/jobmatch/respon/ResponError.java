package com.example.jobmatch.respon;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponError extends RuntimeException {
    private String errorMessage;

    public ResponError(Respon respon) {
        this.errorMessage = respon.getMessageCode();
    }
}
