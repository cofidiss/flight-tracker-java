package com.flight.core.service;


import com.flight.application.DTO.SignUpRequestDto;
import com.flight.core.domain.*;
import jakarta.servlet.http.Cookie;
import org.springframework.web.context.request.RequestAttributes;

import java.util.Optional;
public interface IUserService {

    public GenericApiResponse<String,Cookie> SignUp(SignUpRequestDto signUpRequest);

    public Optional<UserEntity> findUserByUsername(String username);
    public boolean verifyPassword(String rawPassword, String encodedPassword);

    public Cookie Login(String username, String password);
    public boolean hasPermission(String userId, UserRole requiredRole);
    public String GetUserRole (String userId);
    public RoleLov[] GetRolelov();
    public String getUserIdFromCookie(RequestAttributes RequestAttributes);

}
