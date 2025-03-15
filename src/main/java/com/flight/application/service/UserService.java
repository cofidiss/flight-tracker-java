package com.flight.application.service;

import com.flight.application.DTO.SignUpRequestDto;
import com.flight.application.mapper.IUserMapper;
import com.flight.application.util.CustomUtil;
import com.flight.core.domain.*;
import com.flight.core.service.IUserService;
import com.flight.infrastructure.persistence.IUserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private IUserMapper userMapper;
    private IUserRepository userRepository;

    public UserService(IUserMapper userMapper, IUserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    @Override
    public GenericApiResponse<String, Cookie> signUp(SignUpRequestDto signUpRequest) {
        GenericApiResponse<String, Cookie> apiResponse = new GenericApiResponse<>();

        // DTO'yu Entity'ye dönüştür
        UserEntity userEntityToInsert = userMapper.toEntity(signUpRequest);

        // Veritabanına ekle
        userRepository.save(userEntityToInsert);

        Optional<UserEntity> userOpt = userRepository.findByUsername(signUpRequest.getUsername());

        var cookie = createUserCookie(userOpt);
        // Başarı yanıtını ayarla
        apiResponse.setSuccessData(cookie);
        return apiResponse;
    }

    @Override
    public boolean verifyPassword(String rawPassword, String encodedPassword) {
        return CustomUtil.hashPassword(rawPassword).equals(encodedPassword);
    }

    private Cookie createUserCookie(Optional<UserEntity> userOpt) {
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

    @Override
    public GenericApiResponse<String, Cookie> login(String username, String password) {
        GenericApiResponse<String, Cookie> apiResponse = new GenericApiResponse<>();
        Optional<UserEntity> userOpt = userRepository.findByUsername(username)
                .filter(user -> verifyPassword(password, user.getPassword()));

        var cookie = createUserCookie(userOpt);
        apiResponse.setSuccessData(cookie);
        return apiResponse; // Şifre doğrulaması
    }

    @Override
    public String getUserRole(String userId) {
        UserEntity user = userRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        // Rolleri kontrol et
        return UserRole.fromId(user.getRoleId()).getDisplayName();
    }

    @Override
    public RoleLov[] getRoleLov() {
        return Arrays.stream(UserRole.values())
                .map(role -> new RoleLov(role.getDisplayName(), role.getValue())) // UserRole'dan RoleLov nesneleri oluşturuyoruz
                .toArray(RoleLov[]::new); // Sonuçları RoleLov dizisi olarak dönüyoruz
    }

    public String getUserIdFromCookie(RequestAttributes requestAttributes) {
        // Request'ten cookie'yi alın

        if (requestAttributes instanceof ServletRequestAttributes) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("userId".equals(cookie.getName())) {
                        return cookie.getValue();
                    }
                }
            }
        }
        return null; // Cookie'den userId alınamazsa null döner
    }
}
