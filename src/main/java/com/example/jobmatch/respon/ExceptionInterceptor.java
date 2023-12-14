package com.example.jobmatch.respon;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionInterceptor extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ResponError.class)
    public ResponseEntity err(ResponError responError) {
        Respon response = new Respon(responError.getErrorMessage());
        return ResponseEntity.status(400).body(response);
    }
}