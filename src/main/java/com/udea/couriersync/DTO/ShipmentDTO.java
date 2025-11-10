package com.udea.couriersync.DTO;

import com.udea.couriersync.enums.ShipmentPriority;
import com.udea.couriersync.enums.ShipmentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentDTO {
    private Long id;
    private String trackingCode;
    private ClientDTO client;
    private VehicleDTO vehicle;
    private String originAddress;
    private String destinationAddress;
    private Double weight;
    private Double volume;
    private ShipmentPriority priority;
    private ShipmentStatus status;
}
