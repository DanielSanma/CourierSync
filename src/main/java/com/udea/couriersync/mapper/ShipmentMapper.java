package com.udea.couriersync.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.udea.couriersync.dto.ShipmentDTO;
import com.udea.couriersync.entity.Shipment;

@Mapper(componentModel = "spring", uses = { ClientMapper.class, VehicleMapper.class}) 
public interface ShipmentMapper {
	ShipmentMapper INSTANCE = Mappers.getMapper(ShipmentMapper.class);

	ShipmentDTO toDTO(Shipment entity);

	Shipment toEntity(ShipmentDTO dto);
}
