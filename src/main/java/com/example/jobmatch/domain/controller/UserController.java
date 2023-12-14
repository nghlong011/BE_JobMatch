package com.example.jobmatch.domain.controller;

import com.example.jobmatch.domain.service.UserService;
import com.example.jobmatch.domain.user.request.LoginRequest;
import com.example.jobmatch.domain.user.request.RegisterUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterUserRequest registerUserRequest) {
        return ResponseEntity.ok(userService.register(registerUserRequest));
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.login(loginRequest));
    }

    @GetMapping("/logout")
    public ResponseEntity logout() {
        return ResponseEntity.ok(userService.logout());
    }
}
