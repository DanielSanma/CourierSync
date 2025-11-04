package com.udea.couriersync.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.udea.couriersync.DTO.ClientDTO;
import com.udea.couriersync.services.ClientService;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

  @Autowired
  private ClientService clientService;

  @CrossOrigin
  @PostMapping
  public ResponseEntity<ClientDTO> create(@RequestBody ClientDTO dto) {
    ClientDTO created = clientService.createClient(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(created);
  }

  @GetMapping
  public List<ClientDTO> list() {
    return clientService.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<ClientDTO> get(@PathVariable Long id) {
    return clientService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }

  @CrossOrigin
  @PutMapping("/{id}")
  public ResponseEntity<ClientDTO> update(@PathVariable Long id, @RequestBody ClientDTO dto) {
    ClientDTO updated = clientService.update(id, dto);
    return ResponseEntity.ok(updated);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    clientService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
