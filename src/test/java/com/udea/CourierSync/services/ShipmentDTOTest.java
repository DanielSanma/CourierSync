package com.udea.couriersync.services;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.udea.couriersync.DTO.ShipmentDTO;
import com.udea.couriersync.enums.ShipmentStatus; 

class ShipmentDTOTest {

   @Test
    void testShipmentDTO() {
        ShipmentDTO dto = new ShipmentDTO();
        dto.setStatus(ShipmentStatus.ENTREGADO);
        assertEquals(ShipmentStatus.ENTREGADO, dto.getStatus());
    }
}
