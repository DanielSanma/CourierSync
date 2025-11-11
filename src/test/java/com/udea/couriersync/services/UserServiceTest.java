package com.udea.couriersync.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.udea.couriersync.DTO.UserDTO;
import com.udea.couriersync.entity.User;
import com.udea.couriersync.exception.BadRequestException;
import com.udea.couriersync.exception.ResourceNotFoundException;
import com.udea.couriersync.mapper.UserMapper;
import com.udea.couriersync.repository.UserRepository;
import com.udea.couriersync.services.UserService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    void create_shouldThrow_whenDtoNull() {
        assertThrows(BadRequestException.class, () -> userService.create(null));
    }

    @Test
    void create_shouldSaveAndReturnDto() {
        UserDTO dto = new UserDTO(null, "Bob", "b@b.com", "pwd", "123", null);
        User entity = new User();
        entity.setName("Bob");
        User saved = new User();
        saved.setId(1L);
        saved.setName("Bob");
        UserDTO expected = new UserDTO(1L, "Bob", "b@b.com", "pwd", "123", null);

        when(userMapper.toEntity(dto)).thenReturn(entity);
        when(userRepository.save(entity)).thenReturn(saved);
        when(userMapper.toDTO(saved)).thenReturn(expected);

        UserDTO res = userService.create(dto);
        assertNotNull(res);
        assertEquals(1L, res.getId());
        assertEquals("Bob", res.getName());
    }

    @Test
    void update_shouldThrow_whenNotFound() {
        Long id = 20L;
        UserDTO dto = new UserDTO();
        when(userRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> userService.update(id, dto));
    }

    @Test
    void deleteById_shouldThrow_whenNotExists() {
        Long id = 7L;
        when(userRepository.existsById(id)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> userService.deleteById(id));
    }

    @Test
    void findAll_shouldReturnMappedList() {
        User u1 = new User();
        User u2 = new User();
        when(userRepository.findAll()).thenReturn(List.of(u1, u2));
        when(userMapper.toDTO(u1)).thenReturn(new UserDTO());
        when(userMapper.toDTO(u2)).thenReturn(new UserDTO());

        List<UserDTO> all = userService.findAll();
        assertEquals(2, all.size());
    }
}
