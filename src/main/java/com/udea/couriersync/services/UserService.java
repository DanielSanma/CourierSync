package com.udea.couriersync.services;

import org.springframework.stereotype.Service;

import com.udea.couriersync.dto.UserDTO;
import com.udea.couriersync.entity.User;
import com.udea.couriersync.exception.BadRequestException;
import com.udea.couriersync.exception.ResourceNotFoundException;
import com.udea.couriersync.mapper.UserMapper;
import com.udea.couriersync.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    // --- CORRECCIÓN: Inyección por Constructor ---

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Constructor para inyectar todas las dependencias (UserRepository y UserMapper).
     */
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    // --- FIN DE LA CORRECCIÓN ---

    public UserDTO create(UserDTO dto) {
        if (dto == null)
            throw new BadRequestException("UserDTO must not be null");
        User entity = userMapper.toEntity(dto);
        User saved = userRepository.save(entity);
        return userMapper.toDTO(saved);
    }

    public Optional<UserDTO> findById(Long id) {
        return userRepository.findById(id).map(userMapper::toDTO);
    }

    public Optional<UserDTO> findByEmail(String email) {
        return userRepository.findByEmail(email).map(userMapper::toDTO);
    }

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream().map(userMapper::toDTO).toList();
    }

    public UserDTO update(Long id, UserDTO dto) {
        if (dto == null)
            throw new BadRequestException("UserDTO must not be null");
        userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        User toSave = userMapper.toEntity(dto);
        toSave.setId(id);
        User saved = userRepository.save(toSave);
        return userMapper.toDTO(saved);
    }

    public void deleteById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}