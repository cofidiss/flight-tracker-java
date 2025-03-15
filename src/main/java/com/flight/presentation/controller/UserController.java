package com.flight.presentation.controller;

import com.flight.application.DTO.RoleLovDto;
import com.flight.application.DTO.SignUpRequestDto;
import com.flight.application.DTO.LoginRequestDto;
import com.flight.application.interfaces.IUserService;
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
    public String login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        var result = userService.login(loginRequestDto.getUsername(), loginRequestDto.getPassword());
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
    public RoleLovDto[] getRoleLov() {
        return userService.getRoleLov();
    }
}
