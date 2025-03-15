package com.flight.application.mapper;


import com.flight.application.DTO.SignUpRequestDto;
import com.flight.core.domain.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.flight.application.util.CustomUtil;

@Mapper(componentModel = "spring",uses = CustomUtil.class)
public interface IUserMapper {
    @Mapping(target = "username", source = "username")
    @Mapping(target = "password", source = "password", qualifiedByName = "hashPassword")
    @Mapping(target = "roleId", source = "roleId")
    UserEntity toEntity(SignUpRequestDto dto);

}
