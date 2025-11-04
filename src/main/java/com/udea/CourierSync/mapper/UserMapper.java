package com.udea.couriersync.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.udea.couriersync.DTO.UserDTO;
import com.udea.couriersync.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
	UserDTO toDTO(User entity);
	User toEntity(UserDTO dto);
}