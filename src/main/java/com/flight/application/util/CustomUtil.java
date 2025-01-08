package com.flight.application.util;

import org.mapstruct.Named;
import org.springframework.context.annotation.Bean;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class CustomUtil {

    @Named("hashPassword")
     public static String  hashPassword(String password) {
        if (password == null) {
            return null;
        }
        try {
            // SHA-512 hash işlemi
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            byte[] hash = digest.digest(password.getBytes());
            // Hash'i Base64 olarak döndür
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-512 algoritması bulunamadı", e);
        }
    }
}
