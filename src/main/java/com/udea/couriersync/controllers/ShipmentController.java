package com.udea.couriersync.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.udea.couriersync.DTO.ShipmentDTO;
import com.udea.couriersync.enums.ShipmentStatus;
import com.udea.couriersync.services.ShipmentService;

import java.util.List;

@RestController
@RequestMapping("/api/shipments")
public class ShipmentController {

    // --- CORRECCIÓN: Inyección por Constructor ---

    private final ShipmentService shipmentService;

    // Constructor para inyectar la dependencia
    public ShipmentController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    // --- FIN DE LA CORRECCIÓN ---

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or (hasRole('OPERATOR') and #dto.status == 'PENDING')")
    public ResponseEntity<ShipmentDTO> create(@RequestBody ShipmentDTO dto) {
        ShipmentDTO created = shipmentService.createShipment(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'OPERATOR', 'DRIVER')")
    public List<ShipmentDTO> list() {
        return shipmentService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'OPERATOR', 'DRIVER')")
    public ResponseEntity<ShipmentDTO> get(@PathVariable Long id) {
        return shipmentService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or " +
        "(hasRole('OPERATOR') and @shipmentService.isShipmentPending(#id)) or " +
        "(hasRole('DRIVER') and @shipmentService.canDriverUpdateStatus(#id, #dto))")
    public ResponseEntity<ShipmentDTO> update(@PathVariable Long id, @RequestBody ShipmentDTO dto) {
        ShipmentDTO updated = shipmentService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('OPERATOR') and @shipmentService.isShipmentPending(#id))")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        shipmentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'DRIVER')")
    public ResponseEntity<ShipmentDTO> updateStatus(
        @PathVariable Long id,
        @RequestParam ShipmentStatus status,
        @RequestParam(required = false) String observations) {
        ShipmentDTO updated = shipmentService.updateStatus(id, status, observations);
        return ResponseEntity.ok(updated);
    }
}
