package com.flight.presentation.controller;


import com.flight.application.DTO.SignUpRequestDto;
import com.flight.core.domain.LoginRequest;
import com.flight.core.domain.RoleLov;

import com.flight.core.service.IUserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;

@RestController
@RequestMapping("/api/user")
public class UserController {


    private IUserService userService;
    UserController(IUserService userService){
          this.userService = userService;



    }
    @PostMapping("/signup")
    public String Signup(@RequestBody SignUpRequestDto signUpRequest, HttpServletResponse response) {
        var result = userService.SignUp(signUpRequest);
        if (result.isSuccess()) {
            response.addCookie(result.getSuccessData());
            return  "Signup Succesfull";
        }
        return "Signup UnSuccesfull";
    }

    @PostMapping("/login")
    public void login(@RequestBody LoginRequest loginRequest,

                      HttpServletResponse response) {
      var cookie = userService.Login(loginRequest.getUsername(),loginRequest.getPassword());
      response.addCookie(cookie);

    }

    @GetMapping("/getUserRole")
    public String  getRoles(@AuthenticationPrincipal User user ) {
       var userId =  userService. getUserIdFromCookie(RequestContextHolder.getRequestAttributes());
        return userService.GetUserRole(userId);

    }
    @GetMapping("/getRolelov")
    public RoleLov[] getRolelov() {

       return userService.GetRolelov();


    }

}
