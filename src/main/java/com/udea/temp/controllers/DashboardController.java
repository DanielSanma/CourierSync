package com.udea.couriersync.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udea.couriersync.repository.ClientRepository;
import com.udea.couriersync.repository.ShipmentRepository;
import com.udea.couriersync.repository.UserRepository;

import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@PreAuthorize("hasRole('ADMIN')")
public class DashboardController {

  @Autowired
  private ShipmentRepository shipmentRepository;

  @Autowired
  private ClientRepository clientRepository;

  @Autowired
  private UserRepository userRepository;

  @GetMapping("/metrics")
  public Map<String, Object> metrics() {
    long shipments = shipmentRepository.count();
    long clients = clientRepository.count();
    long users = userRepository.count();

    return Map.of(
        "shipments", shipments,
        "clients", clients,
        "users", users);
  }
}
