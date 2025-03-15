package com.flight.application.security;

import com.flight.core.domain.RequiresPermission;

import com.flight.core.domain.UserEntity;
import com.flight.core.domain.UserRole;
import com.flight.core.service.IUserService;
import com.flight.infrastructure.persistence.IUserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Optional;


@Aspect
@Component

public class AuthorizationAspect {


    private final IUserRepository userRepository;
    private final IUserService userService;
    public AuthorizationAspect( IUserRepository userRepository,IUserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }



    @Around("@annotation(com.flight.core.domain.RequiresPermission)")
    public Object checkPermission(ProceedingJoinPoint joinPoint) throws Throwable {

        String methodName = joinPoint.getSignature().getName();
        Class<?>[] parameterTypes = ((org.aspectj.lang.reflect.MethodSignature) joinPoint.getSignature()).getParameterTypes();
        Method method  =  joinPoint.getTarget().getClass().getMethod(methodName, parameterTypes);

        UserRole userRoleRequired = UserRole.StandartUser;
        // Anotasyonu al
        if (method.isAnnotationPresent(RequiresPermission.class)) {
            RequiresPermission customAction = method.getAnnotation(RequiresPermission.class);
            userRoleRequired = customAction.value();

        }

        // Anotasyondan gerekli rol bilgisi
        int requiredRoleId = userRoleRequired.getValue();

        // Cookie'den userId'yi al
        String userId = userService.getUserIdFromCookie(RequestContextHolder.getRequestAttributes());
        if (userId == null) {
            throw new AccessDeniedException("User ID not found in cookies.");
        }

        // Veritabanından kullanıcı bilgilerini al
        Optional<UserEntity> userOptional = userRepository.findById(Long.parseLong(userId));
        if (userOptional.isEmpty() ) {
            throw new AccessDeniedException("User not found.");
        }
        var userEntity = userOptional.get();
        // Kullanıcının rolü ile anotasyondaki rolü karşılaştır
        if (!(userEntity.getRoleId() == requiredRoleId)) {
            throw new AccessDeniedException("User does not have the required permission.");
        }

        // Metodu çalıştır
        return joinPoint.proceed();
    }

    // Cookie'den userId'yi alır

}
