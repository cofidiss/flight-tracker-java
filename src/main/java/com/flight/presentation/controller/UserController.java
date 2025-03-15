package com.flight.presentation.controller;

import com.flight.application.DTO.SignUpRequestDto;
import com.flight.core.domain.LoginRequest;
import com.flight.core.domain.RoleLov;
import com.flight.core.service.IUserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public String signUp(@RequestBody SignUpRequestDto signUpRequest, HttpServletResponse response) {
        var result = userService.signUp(signUpRequest);
        if (result.isSuccess()) {
            response.addCookie(result.getSuccessData());
            return "Signup Successful";
        }
        return "Signup Unsuccessful";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        var result = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
        if (result.isSuccess()) {
            response.addCookie(result.getSuccessData());
            return "Login Successful";
        }
        return "Login Unsuccessful";
    }

    @GetMapping("/getUserRole")
    public String getUserRole() {
        var userId = userService.getUserIdFromCookie(RequestContextHolder.getRequestAttributes());
        return userService.getUserRole(userId);
    }

    @GetMapping("/getRoleLov")
    public RoleLov[] getRoleLov() {
        return userService.getRoleLov();
    }
}
