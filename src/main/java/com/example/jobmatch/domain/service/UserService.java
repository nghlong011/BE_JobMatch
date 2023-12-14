package com.example.jobmatch.domain.service;

import com.example.jobmatch.auth.JwtUtility;
import com.example.jobmatch.domain.entity.UserEntity;
import com.example.jobmatch.domain.repository.RoleRepo;
import com.example.jobmatch.domain.repository.UserRepo;
import com.example.jobmatch.domain.user.request.LoginRequest;
import com.example.jobmatch.domain.user.request.RegisterUserRequest;
import com.example.jobmatch.domain.user.response.RegisterResponse;
import com.example.jobmatch.respon.Respon;
import com.example.jobmatch.respon.ResponError;
import com.example.jobmatch.seeder.Enum.RoleEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

@Component
public class UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private JwtUtility jwtUtility;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private HttpServletResponse httpServletResponse;

    public Respon register(RegisterUserRequest registerUserRequest) {
        try {
            if (userRepo.findByEmail(registerUserRequest.getEmail()) != null) {
                throw new ResponError(new Respon<>("Đăng kí thất bại"));
            }
            UserEntity userEntity = modelMapper.map(registerUserRequest, UserEntity.class);
            userEntity.setPassword(passwordEncoder.encode(registerUserRequest.getPassword()));
            userEntity.setRoleEntity(roleRepo.findByRoleName(RoleEnum.USER.getRoleName()));
            userRepo.save(userEntity);
            return new Respon<>("Đăng kí thành công");
        } catch (Exception e) {
            return new Respon<>("Thất bại");
        }
    }

    public Respon login(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassWord()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtUtility.generateToken(loginRequest.getUserName());
            UserEntity userEntity = userRepo.findByEmail(loginRequest.getUserName());

            userEntity.setToken(token);
            userRepo.save(userEntity);

            RegisterResponse registerRes = new RegisterResponse();
            registerRes.setToken(token);
            return new Respon<>("Đăng nhập thành công", registerRes);
        } catch (Exception e) {
            return new Respon<>("Đăng nhập thất bại");
        }
    }

    public Respon logout() {
        try {
            UserEntity userEntity = userRepo.findByEmail(jwtUtility.userDetails().getUsername());
            userEntity.setToken(null);
            userRepo.save(userEntity);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            new SecurityContextLogoutHandler().logout(httpServletRequest, httpServletResponse, authentication);
            return new Respon<>("Đăng xuất thành công");
        } catch (Exception e) {
            return new Respon<>("Thất bại");
        }
    }
}
