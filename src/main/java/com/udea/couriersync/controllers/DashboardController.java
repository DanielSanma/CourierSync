package com.udea.couriersync.controllers;

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

    // --- CORRECCIÓN: Se eliminan los @Autowired de campo y se declaran como final ---

    private final ShipmentRepository shipmentRepository;
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;

    /**
     * Constructor para la Inyección de Dependencias.
     * Spring inyecta automáticamente las implementaciones de los repositorios.
     */
    public DashboardController(
            ShipmentRepository shipmentRepository,
            ClientRepository clientRepository,
            UserRepository userRepository) {
        this.shipmentRepository = shipmentRepository;
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
    }

    // --- FIN DE LA CORRECCIÓN ---

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