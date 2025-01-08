package com.flight.presentation.controller;


import com.flight.core.domain.GenericApiResponse;
import com.flight.core.domain.SignUpRequest;
import com.flight.core.service.IAuthService;
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
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        return authService.findUserByUsername(username)
                .filter(user -> authService.verifyPassword(password, user.getPassword()))
                .map(user -> ResponseEntity.ok("Login successful"))
                .orElse(ResponseEntity.status(401).body("Invalid credentials"));
    }}

