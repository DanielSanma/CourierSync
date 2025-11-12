package com.udea.couriersync.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.udea.couriersync.dto.ClientDTO;
import com.udea.couriersync.entity.Client;

@Mapper(componentModel = "spring")
public interface ClientMapper {
  ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);
	ClientDTO toDTO(Client entity);
	Client toEntity(ClientDTO dto);
}

