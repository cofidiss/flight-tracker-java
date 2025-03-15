package com.flight.core.domain;


import lombok.Getter;

import java.util.Arrays;

@Getter
public enum UserRole {
    Admin(1,"Administrator"),
    StandartUser(2,"StandardUser"),
    PremiumUser(3,"PremiumUser");

    private final int value;           // Role'un int değeri
    private final String displayName;

    UserRole(int value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }
    public static UserRole fromId(int id) {
        return Arrays.stream(values()).filter(role -> role.getValue()== id).findFirst().orElseThrow(() -> new IllegalArgumentException("Invalid ID: " + id));
    }

}