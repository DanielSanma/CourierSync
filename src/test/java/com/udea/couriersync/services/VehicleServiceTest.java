package com.udea.couriersync.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.udea.couriersync.dto.VehicleDTO;
import com.udea.couriersync.entity.Vehicle;
import com.udea.couriersync.exception.BadRequestException;
import com.udea.couriersync.exception.ResourceNotFoundException;
import com.udea.couriersync.mapper.VehicleMapper;
import com.udea.couriersync.repository.VehicleRepository;
import com.udea.couriersync.services.VehicleService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehicleServiceTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private VehicleMapper vehicleMapper;

    @InjectMocks
    private VehicleService vehicleService;

    @Test
    void createVehicle_shouldThrow_whenDtoNull() {
        assertThrows(BadRequestException.class, () -> vehicleService.createVehicle(null));
    }

    @Test
    void createVehicle_shouldSaveAndReturnDto() {
        VehicleDTO dto = new VehicleDTO(null, "ABC123", "ModelX", 1000.0, true);
        Vehicle entity = new Vehicle();
        entity.setPlate("ABC123");
        Vehicle saved = new Vehicle();
        saved.setId(1L);
        saved.setPlate("ABC123");
        VehicleDTO expected = new VehicleDTO(1L, "ABC123", "ModelX", 1000.0, true);

        when(vehicleMapper.toEntity(dto)).thenReturn(entity);
        when(vehicleRepository.save(entity)).thenReturn(saved);
        when(vehicleMapper.toDTO(saved)).thenReturn(expected);

        VehicleDTO res = vehicleService.createVehicle(dto);
        assertNotNull(res);
        assertEquals(1L, res.getId());
        assertEquals("ABC123", res.getPlate());
    }

    @Test
    void update_shouldThrow_whenNotFound() {
        Long id = 33L;
        VehicleDTO dto = new VehicleDTO();
        when(vehicleRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> vehicleService.update(id, dto));
    }

    @Test
    void deleteById_shouldThrow_whenNotExists() {
        Long id = 11L;
        when(vehicleRepository.existsById(id)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> vehicleService.deleteById(id));
    }

    @Test
    void findAll_shouldReturnMappedList() {
        Vehicle v1 = new Vehicle();
        Vehicle v2 = new Vehicle();
        when(vehicleRepository.findAll()).thenReturn(List.of(v1, v2));
        when(vehicleMapper.toDTO(v1)).thenReturn(new VehicleDTO());
        when(vehicleMapper.toDTO(v2)).thenReturn(new VehicleDTO());

        List<VehicleDTO> all = vehicleService.findAll();
        assertEquals(2, all.size());
    }
}
