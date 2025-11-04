package com.udea.couriersync.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udea.couriersync.DTO.ClientDTO;
import com.udea.couriersync.entity.Client;
import com.udea.couriersync.exception.BadRequestException;
import com.udea.couriersync.exception.ResourceNotFoundException;
import com.udea.couriersync.mapper.ClientMapper;
import com.udea.couriersync.repository.ClientRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

  @Autowired
  private ClientRepository clientRepository;

  @Autowired
  private ClientMapper clientMapper;

  public ClientDTO createClient(ClientDTO dto) {
    if (dto == null)
      throw new BadRequestException("ClientDTO must not be null");
    Client entity = clientMapper.toEntity(dto);
    Client saved = clientRepository.save(entity);
    return clientMapper.toDTO(saved);
  }

  public Optional<ClientDTO> findById(Long id) {
    return clientRepository.findById(id).map(clientMapper::toDTO);
  }

  public List<ClientDTO> findAll() {
    return clientRepository.findAll().stream().map(clientMapper::toDTO).toList();
  }

  public ClientDTO update(Long id, ClientDTO dto) {
    if (dto == null)
      throw new BadRequestException("ClientDTO must not be null");
    clientRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));
    Client toSave = clientMapper.toEntity(dto);
    toSave.setId(id);
    Client saved = clientRepository.save(toSave);
    return clientMapper.toDTO(saved);
  }

  public void deleteById(Long id) {
    if (!clientRepository.existsById(id)) {
      throw new ResourceNotFoundException("Client not found with id: " + id);
    }
    clientRepository.deleteById(id);
  }
}
