package com.udea.couriersync.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.udea.couriersync.dto.VehicleDTO;
import com.udea.couriersync.entity.Vehicle;

@Mapper(componentModel = "spring")
public interface VehicleMapper {
  VehicleMapper INSTANCE = Mappers.getMapper(VehicleMapper.class);
	VehicleDTO toDTO(Vehicle entity);
	Vehicle toEntity(VehicleDTO dto);
}

