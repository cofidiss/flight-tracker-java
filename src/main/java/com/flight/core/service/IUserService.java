package com.flight.core.service;

import com.flight.application.DTO.SignUpRequestDto;
import com.flight.core.domain.*;
import jakarta.servlet.http.Cookie;
import org.springframework.web.context.request.RequestAttributes;

public interface IUserService {

    public GenericApiResponse<String,Cookie> signUp(SignUpRequestDto signUpRequest);

    public boolean verifyPassword(String rawPassword, String encodedPassword);

    public GenericApiResponse<String,Cookie> login(String username, String password);

    public String getUserRole (String userId);
    public RoleLov[] getRoleLov();
    public String getUserIdFromCookie(RequestAttributes RequestAttributes);

}
