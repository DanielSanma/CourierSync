package com.udea.couriersync.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.udea.couriersync.DTO.ClientDTO;
import com.udea.couriersync.entity.Client;
import com.udea.couriersync.exception.BadRequestException;
import com.udea.couriersync.exception.ResourceNotFoundException;
import com.udea.couriersync.mapper.ClientMapper;
import com.udea.couriersync.repository.ClientRepository;
import com.udea.couriersync.services.ClientService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientMapper clientMapper;

    @InjectMocks
    private ClientService clientService;

    @Test
    void createClient_shouldReturnMappedDto_whenSuccessful() {
        ClientDTO dto = new ClientDTO(null, "Alice", "a@example.com", "123", "addr");
        Client entity = new Client();
        entity.setName("Alice");
        entity.setEmail("a@example.com");
        entity.setPhone("123");
        entity.setAddress("addr");
        Client saved = new Client();
        saved.setId(1L);
        saved.setName("Alice");
        saved.setEmail("a@example.com");
        saved.setPhone("123");
        saved.setAddress("addr");
        ClientDTO expected = new ClientDTO(1L, "Alice", "a@example.com", "123", "addr");

        when(clientMapper.toEntity(dto)).thenReturn(entity);
        when(clientRepository.save(entity)).thenReturn(saved);
        when(clientMapper.toDTO(saved)).thenReturn(expected);

        ClientDTO result = clientService.createClient(dto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Alice", result.getName());
        assertEquals("a@example.com", result.getEmail());

        verify(clientMapper).toEntity(dto);
        verify(clientRepository).save(entity);
        verify(clientMapper).toDTO(saved);
    }

    @Test
    void createClient_shouldThrow_whenDtoIsNull() {
        assertThrows(BadRequestException.class, () -> clientService.createClient(null));
    }

    @Test
    void update_shouldThrow_whenNotFound() {
        Long id = 10L;
        ClientDTO dto = new ClientDTO();
        when(clientRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> clientService.update(id, dto));
    }

    @Test
    void deleteById_shouldThrow_whenNotExists() {
        Long id = 5L;
        when(clientRepository.existsById(id)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> clientService.deleteById(id));
    }

    @Test
    void findAll_shouldReturnMappedList() {
        Client c1 = new Client();
        c1.setName("A");
        Client c2 = new Client();
        c2.setName("B");
        when(clientRepository.findAll()).thenReturn(List.of(c1, c2));
        when(clientMapper.toDTO(c1)).thenReturn(new ClientDTO());
        when(clientMapper.toDTO(c2)).thenReturn(new ClientDTO());

        List<com.udea.couriersync.DTO.ClientDTO> all = clientService.findAll();
        assertEquals(2, all.size());
        verify(clientRepository).findAll();
    }

    @Test
    void findById_shouldReturnMapped_whenPresent() {
        Long id = 2L;
        Client c = new Client();
        c.setName("A");
        when(clientRepository.findById(id)).thenReturn(Optional.of(c));
        when(clientMapper.toDTO(c)).thenReturn(new ClientDTO());

        Optional<ClientDTO> opt = clientService.findById(id);
        assertTrue(opt.isPresent());
        verify(clientRepository).findById(id);
    }
}
