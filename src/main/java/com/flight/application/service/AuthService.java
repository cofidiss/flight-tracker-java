package com.flight.application.service;

import com.flight.application.mapper.IUserMapper;
import com.flight.application.util.SHA512PasswordEncoder;
import com.flight.core.domain.GenericApiResponse;
import com.flight.core.domain.SignUpRequest;
import com.flight.core.domain.UserEntity;
import com.flight.core.domain.UserRole;
import com.flight.core.service.IAuthService;
import com.flight.infrastructure.persistence.IUserRepository;
import jakarta.servlet.http.Cookie;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService implements IAuthService {



    private IUserMapper userMapper;
    private final SHA512PasswordEncoder passwordEncoder;

    private IUserRepository userRepository;
    AuthService(IUserMapper userMapper, IUserRepository userRepository,SHA512PasswordEncoder passwordEncoder
                ){

        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

}

    public GenericApiResponse<String, Long> SignUp(SignUpRequest signUpRequest) {
        GenericApiResponse<String, Long> apiResponse = new GenericApiResponse<>();

        // DTO'yu Entity'ye dönüştür
        UserEntity userEntityToInsert = userMapper.toEntity(signUpRequest);

        // Veritabanına ekle
        userRepository.save(userEntityToInsert);

        // Başarı yanıtını ayarla
        apiResponse.setSuccessData(userEntityToInsert.getId());
        return apiResponse;
    }

    public Optional<UserEntity> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean verifyPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.encode(rawPassword).equals(encodedPassword) ;
    }

    @Override
    public Cookie Login(String username, String password) {

        Optional<UserEntity> userOpt = userRepository.findByUsername(username)
                .filter(user -> verifyPassword(password, user.getPassword())); // Şifre doğrulaması

        return userOpt
                .map(user -> {
                    // Şifre doğruysa, UserEntity nesnesini alıyoruz
                    UserEntity userEntity = user;

                    // Cookie oluştur ve ekle
                    Cookie userIdCookie = new Cookie("userId", userEntity.getId().toString());
                    userIdCookie.setHttpOnly(true);  // Cookie güvenliği için HttpOnly
                    userIdCookie.setSecure(true);    // HTTPS için Secure olarak ayarla (HTTPS kullanıyorsanız)
                    userIdCookie.setPath("/");       // Cookie tüm site için geçerli
                    userIdCookie.setMaxAge(7 * 24 * 60 * 60); // 1 hafta geçerlilik

                    return userIdCookie;
                })
                .orElse(null);
    }
    public boolean hasPermission(String userId, UserRole requiredRole) {
        // Veritabanından kullanıcıyı al
        UserEntity user = userRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        // Rolleri kontrol et
        return  user.getRoleId() ==requiredRole.ordinal() ;

    }
}
