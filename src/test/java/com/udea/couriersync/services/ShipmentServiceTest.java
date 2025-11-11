package com.udea.couriersync.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.udea.couriersync.DTO.ClientDTO;
import com.udea.couriersync.DTO.ShipmentDTO;
import com.udea.couriersync.entity.Client;
import com.udea.couriersync.entity.Shipment;
import com.udea.couriersync.enums.ShipmentStatus;
import com.udea.couriersync.exception.BadRequestException;
import com.udea.couriersync.exception.ResourceNotFoundException;
import com.udea.couriersync.mapper.ShipmentMapper;
import com.udea.couriersync.repository.ClientRepository;
import com.udea.couriersync.repository.ShipmentRepository;
import com.udea.couriersync.services.ShipmentService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShipmentServiceTest {

    @Mock
    private ShipmentRepository shipmentRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ShipmentMapper shipmentMapper;

    @InjectMocks
    private ShipmentService shipmentService;

    @Test
    void createShipment_shouldThrow_whenDtoNull() {
        assertThrows(BadRequestException.class, () -> shipmentService.createShipment(null));
    }

    @Test
    void createShipment_shouldThrow_whenClientIdNull() {
        ShipmentDTO dto = new ShipmentDTO();
        dto.setClient(new ClientDTO());

        // Si el mapper devuelve null, el servicio lanzará NPE. Mockear el mapper para devolver una entidad
        Shipment s = new Shipment();
        s.setClient(null); // simula que el mapper no llenó client id
        when(shipmentMapper.toEntity(dto)).thenReturn(s);

        assertThrows(BadRequestException.class, () -> shipmentService.createShipment(dto));
    }

    @Test
    void createShipment_shouldThrow_whenClientNotFound() {
        ShipmentDTO dto = new ShipmentDTO();
        ClientDTO cdto = new ClientDTO();
        cdto.setId(5L);
        dto.setClient(cdto);

        Shipment s = new Shipment();
        Client mappedClient = new Client();
        mappedClient.setId(5L); // important: mapper returns an entity with client id set
        s.setClient(mappedClient);

        when(shipmentMapper.toEntity(dto)).thenReturn(s);
        when(clientRepository.findById(5L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> shipmentService.createShipment(dto));
    }

    @Test
    void createShipment_shouldSaveAndReturnDto_whenHappyPath() {
        ShipmentDTO dto = new ShipmentDTO();
        ClientDTO cdto = new ClientDTO();
        cdto.setId(1L);
        dto.setClient(cdto);

        Shipment s = new Shipment();
        Client mappedClient = new Client();
        mappedClient.setId(1L); // mapper returns entity with client id
        s.setClient(mappedClient);
        Shipment saved = new Shipment();
        saved.setTrackingCode("CS1234567");

        when(shipmentMapper.toEntity(dto)).thenReturn(s);
        when(clientRepository.findById(1L)).thenReturn(Optional.of(mappedClient));
        when(shipmentRepository.save(any(Shipment.class))).thenReturn(saved);
        when(shipmentMapper.toDTO(saved)).thenReturn(dto);

        ShipmentDTO res = shipmentService.createShipment(dto);
        assertNotNull(res);
        verify(shipmentRepository).save(any(Shipment.class));
    }

    @Test
    void findByTrackingCode_shouldThrow_whenBlank() {
        assertThrows(BadRequestException.class, () -> shipmentService.findByTrackingCode("  "));
    }

    @Test
    void isShipmentPending_returnsFalse_whenNotFound() {
        when(shipmentRepository.findById(1L)).thenReturn(Optional.empty());
        assertFalse(shipmentService.isShipmentPending(1L));
    }

    @Test
    void canDriverUpdateStatus_variousTransitions() {
        Shipment s = new Shipment();
        s.setStatus(ShipmentStatus.PENDIENTE);
        when(shipmentRepository.findById(2L)).thenReturn(Optional.of(s));

        ShipmentDTO dto = new ShipmentDTO();
        dto.setStatus(ShipmentStatus.EN_TRANSITO);

        assertTrue(shipmentService.canDriverUpdateStatus(2L, dto));

        s.setStatus(ShipmentStatus.EN_TRANSITO);
        dto.setStatus(ShipmentStatus.ENTREGADO);
        assertTrue(shipmentService.canDriverUpdateStatus(2L, dto));

        dto.setStatus(ShipmentStatus.PENDIENTE);
        assertFalse(shipmentService.canDriverUpdateStatus(2L, dto));
    }

    @Test
    void updateStatus_shouldThrow_whenNotFound() {
        when(shipmentRepository.findById(9L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> shipmentService.updateStatus(9L, ShipmentStatus.ENTREGADO, null));
    }
}
