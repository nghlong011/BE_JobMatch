package com.example.jobmatch.domain.user;

import com.example.jobmatch.domain.user.request.ChangePasswordRequest;
import com.example.jobmatch.domain.user.request.LoginRequest;
import com.example.jobmatch.domain.user.request.RegisterUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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

    @PostMapping("/changePassword")
    public ResponseEntity login(Principal principal, @RequestBody ChangePasswordRequest changePasswordRequest) {
        return ResponseEntity.ok(userService.changePassword(principal, changePasswordRequest));
    }

    @GetMapping("/logout")
    public ResponseEntity logout() {
        return ResponseEntity.ok(userService.logout());
    }
}
