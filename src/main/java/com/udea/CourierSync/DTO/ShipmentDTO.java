package com.udea.couriersync.DTO;

import com.udea.couriersync.enums.ShipmentPriority;
import com.udea.couriersync.enums.ShipmentStatus;

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

    public ShipmentDTO() {
    }

    public ShipmentDTO(Long id, String trackingCode, ClientDTO client, VehicleDTO vehicle,
            String originAddress, String destinationAddress, Double weight, Double volume,
            ShipmentPriority priority, ShipmentStatus status) {
        this.id = id;
        this.trackingCode = trackingCode;
        this.client = client;
        this.vehicle = vehicle;
        this.originAddress = originAddress;
        this.destinationAddress = destinationAddress;
        this.weight = weight;
        this.volume = volume;
        this.priority = priority;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTrackingCode() {
        return trackingCode;
    }

    public void setTrackingCode(String trackingCode) {
        this.trackingCode = trackingCode;
    }

    public ClientDTO getClient() {
        return client;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }

    public VehicleDTO getVehicle() {
        return vehicle;
    }

    public void setVehicle(VehicleDTO vehicle) {
        this.vehicle = vehicle;
    }

    public String getOriginAddress() {
        return originAddress;
    }

    public void setOriginAddress(String originAddress) {
        this.originAddress = originAddress;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public ShipmentPriority getPriority() {
        return priority;
    }

    public void setPriority(ShipmentPriority priority) {
        this.priority = priority;
    }

    public ShipmentStatus getStatus() {
        return status;
    }

    public void setStatus(ShipmentStatus status) {
        this.status = status;
    }
}