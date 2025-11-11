package com.udea.couriersync.services;

import org.springframework.stereotype.Service;

import com.udea.couriersync.DTO.VehicleDTO;
import com.udea.couriersync.entity.Vehicle;
import com.udea.couriersync.exception.BadRequestException;
import com.udea.couriersync.exception.ResourceNotFoundException;
import com.udea.couriersync.mapper.VehicleMapper;
import com.udea.couriersync.repository.VehicleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    // --- CORRECCIÓN: Inyección por Constructor ---
    
    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;

    /**
     * Constructor para inyectar todas las dependencias (VehicleRepository y VehicleMapper).
     */
    public VehicleService(VehicleRepository vehicleRepository, VehicleMapper vehicleMapper) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleMapper = vehicleMapper;
    }

    // --- FIN DE LA CORRECCIÓN ---

    public VehicleDTO createVehicle(VehicleDTO dto) {
        if (dto == null)
            throw new BadRequestException("VehicleDTO must not be null");
        Vehicle entity = vehicleMapper.toEntity(dto);
        Vehicle saved = vehicleRepository.save(entity);
        return vehicleMapper.toDTO(saved);
    }

    public Optional<VehicleDTO> findById(Long id) {
        return vehicleRepository.findById(id).map(vehicleMapper::toDTO);
    }

    public Optional<VehicleDTO> findByPlate(String plate) {
        return vehicleRepository.findByPlate(plate).map(vehicleMapper::toDTO);
    }

    public List<VehicleDTO> findAll() {
        return vehicleRepository.findAll().stream().map(vehicleMapper::toDTO).toList();
    }

    public VehicleDTO update(Long id, VehicleDTO dto) {
        if (dto == null)
            throw new BadRequestException("VehicleDTO must not be null");
        vehicleRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + id));
        Vehicle toSave = vehicleMapper.toEntity(dto);
        toSave.setId(id);
        Vehicle saved = vehicleRepository.save(toSave);
        return vehicleMapper.toDTO(saved);
    }

    public void deleteById(Long id) {
        if (!vehicleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicle not found with id: " + id);
        }
        vehicleRepository.deleteById(id);
    }
}