package com.flight.application.security;

import com.flight.application.interfaces.IUserService;
import com.flight.core.domain.RequiresPermission;
import com.flight.core.domain.UserEntity;
import com.flight.core.domain.UserRole;
import com.flight.infrastructure.persistence.IUserRepository;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

import java.lang.reflect.Method;
import java.util.Optional;

@Aspect
@Component
public class AuthorizationAspect {

    private final IUserRepository userRepository;
    private final IUserService userService;

    public AuthorizationAspect(IUserRepository userRepository, IUserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Around("@annotation(com.flight.core.domain.RequiresPermission)")
    public Object checkPermission(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        Class<?>[] parameterTypes = ((org.aspectj.lang.reflect.MethodSignature) joinPoint.getSignature()).getParameterTypes();
        Method method = joinPoint.getTarget().getClass().getMethod(methodName, parameterTypes);

        UserRole userRoleRequired = UserRole.StandartUser;
        // Get the annotation
        if (method.isAnnotationPresent(RequiresPermission.class)) {
            RequiresPermission customAction = method.getAnnotation(RequiresPermission.class);
            userRoleRequired = customAction.value();
        }

        // Role ID required from annotation
        int requiredRoleId = userRoleRequired.getValue();

        // Get userId from cookie
        String userId = userService.getUserIdFromCookie(RequestContextHolder.getRequestAttributes());
        if (userId == null) {
            throw new AccessDeniedException("User ID not found in cookies.");
        }

        // Retrieve user details from database
        Optional<UserEntity> userOptional = userRepository.findById(Long.parseLong(userId));
        if (userOptional.isEmpty()) {
            throw new AccessDeniedException("User not found.");
        }
        var userEntity = userOptional.get();

        // Compare user's role with the required role from annotation
        if (!(userEntity.getRoleId() == requiredRoleId)) {
            throw new AccessDeniedException("User does not have the required permission.");
        }

        // Proceed with the method execution
        return joinPoint.proceed();
    }
}
