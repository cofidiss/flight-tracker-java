package com.flight.application.mapper;

import com.flight.core.domain.SignUpRequest;
import com.flight.core.domain.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import com.flight.application.util.CustomUtil;

@Mapper(componentModel = "spring",uses = CustomUtil.class)
public interface IUserMapper {
    @Mapping(target = "username", source = "username")
    @Mapping(target = "password", source = "password", qualifiedByName = "hashPassword")
    @Mapping(target = "roleId", source = "roleId")
    UserEntity toEntity(SignUpRequest dto);

}
