package com.flight.presentation.controller;


import com.flight.core.domain.GenericApiResponse;
import com.flight.core.domain.SignUpRequest;
import com.flight.core.service.IAuthService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {


    private IAuthService authService;
    AuthController(IAuthService authService){
          this.authService = authService;


    }
    @PostMapping("/signup")
    public GenericApiResponse<String,Long> Signup(@RequestBody SignUpRequest signUpRequest) {
        return authService.SignUp(signUpRequest);
    }

    @PostMapping("/login")
    public void login(@RequestParam String username,
                                   @RequestParam String password,
                                   HttpServletResponse response) {

           var  cookie = authService.Login(username,password);
response.addCookie(cookie);

    } }
