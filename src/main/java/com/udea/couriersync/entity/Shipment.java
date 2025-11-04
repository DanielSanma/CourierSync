package com.udea.couriersync.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.udea.couriersync.enums.ShipmentPriority;
import com.udea.couriersync.enums.ShipmentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "Shipment")
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tracking_code", nullable = false, unique = true)
    private String trackingCode;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @OneToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @Column(name = "origin_address", nullable = false)
    private String originAddress;

    @Column(name = "destination_address", nullable = false)
    private String destinationAddress;

    @Column(nullable = false)
    private Double weight;

    @Column(nullable = false)
    private Double volume;

    @Enumerated(EnumType.STRING)
    private ShipmentPriority priority;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ShipmentStatus status;

    public Shipment() {
    }

    @JsonCreator
    public Shipment(@JsonProperty("trackingCode") String trackingCode,
            @JsonProperty("client") Client client,
            @JsonProperty("vehicle") Vehicle vehicle,
            @JsonProperty("originAddress") String originAddress,
            @JsonProperty("destinationAddress") String destinationAddress,
            @JsonProperty("weight") Double weight,
            @JsonProperty("volume") Double volume,
            @JsonProperty("priority") ShipmentPriority priority,
            @JsonProperty("status") ShipmentStatus status) {
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
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