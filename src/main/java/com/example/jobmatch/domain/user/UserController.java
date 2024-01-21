package com.example.jobmatch.domain.user;

import com.example.jobmatch.domain.user.request.ChangePasswordRequest;
import com.example.jobmatch.domain.user.request.LoginRequest;
import com.example.jobmatch.domain.user.request.RegisterUserRequest;
import com.example.jobmatch.domain.user.request.GetUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyRole('JOB_SEEKER','ADMIN')")
    public ResponseEntity login(Principal principal, @RequestBody ChangePasswordRequest changePasswordRequest) {
        return ResponseEntity.ok(userService.changePassword(principal, changePasswordRequest));
    }

    @GetMapping("/logout")
    @PreAuthorize("hasAnyRole('JOB_SEEKER','ADMIN')")
    public ResponseEntity logout() {
        return ResponseEntity.ok(userService.logout());
    }

    @GetMapping("/getUserByJobAppId")
    @PreAuthorize("hasAnyRole('EMPLOYER','ADMIN')")
    public ResponseEntity getUserByJobAppId(@RequestBody GetUserRequest getUserRequest) {
        return ResponseEntity.ok(userService.getById(getUserRequest));
    }

    @GetMapping("/del/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity delete(@PathVariable("userId") Integer userId) {
        return ResponseEntity.ok(userService.delete(userId));
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity getAll() {
        return ResponseEntity.ok(userService.getAll());
    }
}
