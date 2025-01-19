package com.flight.core.service;

import com.flight.core.domain.GenericApiResponse;
import com.flight.core.domain.SignUpRequest;
import com.flight.core.domain.UserEntity;
import com.flight.core.domain.UserRole;
import jakarta.servlet.http.Cookie;
import org.springframework.http.HttpStatus;
import java.util.Optional;
public interface IAuthService {

    public GenericApiResponse<String,Long> SignUp(SignUpRequest signUpRequest);

    public Optional<UserEntity> findUserByUsername(String username);
    public boolean verifyPassword(String rawPassword, String encodedPassword);

    public Cookie Login(String username, String password);
    public boolean hasPermission(String userId, UserRole requiredRole);


}
