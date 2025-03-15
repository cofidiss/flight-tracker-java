package com.flight.core.domain;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private  String password;
}
