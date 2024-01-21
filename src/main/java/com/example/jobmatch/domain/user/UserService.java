package com.example.jobmatch.domain.user;

import com.example.jobmatch.auth.JwtUtility;
import com.example.jobmatch.entity.JobApplicationEntity;
import com.example.jobmatch.entity.UserEntity;
import com.example.jobmatch.domain.jobApplication.JobAppRepo;
import com.example.jobmatch.domain.user.request.ChangePasswordRequest;
import com.example.jobmatch.domain.user.request.LoginRequest;
import com.example.jobmatch.domain.user.request.RegisterUserRequest;
import com.example.jobmatch.domain.user.request.GetUserRequest;
import com.example.jobmatch.domain.user.response.LoginResponse;
import com.example.jobmatch.domain.user.response.UserResponse;
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

import java.security.Principal;
import java.util.List;

@Component
public class UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private JobAppRepo jobAppRepo;
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
            userEntity.setRoleEntity(roleRepo.findByRoleName(registerUserRequest.getRole()));
            userRepo.save(userEntity);
            return new Respon<>("Đăng kí thành công");
        } catch (Exception e) {
            return new Respon<>("Thất bại");
        }
    }

    public Respon login(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtUtility.generateToken(loginRequest.getEmail());
            UserEntity userEntity = userRepo.findByEmail(loginRequest.getEmail());

            userEntity.setToken(token);
            userRepo.save(userEntity);

            LoginResponse loginResponse = new LoginResponse(token);
            return new Respon<>("Đăng nhập thành công", loginResponse);
        } catch (Exception e) {
            return new Respon<>("Đăng nhập thất bại", e);
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


    public Respon changePassword(Principal principal, ChangePasswordRequest changePasswordRequest) {
        try {
            UserEntity userEntity = userRepo.findByEmail(principal.getName());
            if (!passwordEncoder.matches(changePasswordRequest.getOldPassword(), userEntity.getPassword())) {
                return new Respon<>("Sai mật khẩu cũ");
            }
            userEntity.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
            userRepo.save(userEntity);
            return new Respon<>("Đổi mật khẩu thành công");
        } catch (Exception e) {
            return new Respon<>("Đổi mật khẩu thất bại");
        }
    }

    public Respon getById(GetUserRequest getUserRequest) {
        try {
            JobApplicationEntity applicationEntity = jobAppRepo.findById(getUserRequest.getIdJobApp()).get();
            UserResponse userEntity = new UserResponse(applicationEntity.getUserEntity().getUserId(), applicationEntity.getUserEntity().getEmail(),
                    applicationEntity.getUserEntity().getName(), applicationEntity.getUserEntity().getPhone(),
                    applicationEntity.getUserEntity().getAddress(), applicationEntity.getUserEntity().getGender(),
                    applicationEntity.getUserEntity().getDob(), applicationEntity.getUserEntity().getAvatar());
            return new Respon<>("Tìm kiếm user thành công", userEntity);
        } catch (Exception e) {
            return new Respon<>("Đổi mật khẩu thất bại");
        }
    }

    public Respon delete(Integer userId) {
        try {
            UserEntity userEntity = userRepo.findById(userId).get();
            userEntity.setRoleEntity(null);
            userRepo.save(userEntity);
            return new Respon<>("Xoá user thành công");
        } catch (Exception e) {
            return new Respon<>("Xoá user thất bại");
        }
    }

    public Respon getAll() {
        try {
            List<UserEntity> userEntity = userRepo.findAllUser();
            return new Respon<>("Get user thành công", userEntity);
        } catch (Exception e) {
            return new Respon<>("Get user thất bại");
        }
    }
}
