package com.flight.application.service;

import com.flight.application.mapper.IUserMapper;
import com.flight.application.util.CustomUtil;
import com.flight.core.domain.GenericApiResponse;
import com.flight.core.domain.SignUpRequest;
import com.flight.core.domain.UserEntity;
import com.flight.core.service.IAuthService;
import com.flight.infrastructure.persistence.IUserRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService implements IAuthService {



    private IUserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    private IUserRepository userRepository;
    AuthService(IUserMapper userMapper, IUserRepository userRepository){

        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = new MessageDigestPasswordEncoder("SHA-512");
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
        return CustomUtil.hashPassword(rawPassword).equals(encodedPassword) ;
    }

}
