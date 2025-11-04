package com.udea.couriersync.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.udea.couriersync.enums.ShipmentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class StatusHistory {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "shipment_id", nullable = false)
  private Shipment shipment;

  @Enumerated(EnumType.STRING)
  @Column(name = "old_status", nullable = false)
  private ShipmentStatus oldStatus;

  @Enumerated(EnumType.STRING)
  @Column(name = "new_status", nullable = false)
  private ShipmentStatus newStatus;

  public StatusHistory() {
  }

  @JsonCreator
  public StatusHistory(@JsonProperty("id") Long id,
      @JsonProperty("shipment") Shipment shipment,
      @JsonProperty("oldStatus") ShipmentStatus oldStatus,
      @JsonProperty("newStatus") ShipmentStatus newStatus) {
    this.id = id;
    this.shipment = shipment;
    this.oldStatus = oldStatus;
    this.newStatus = newStatus;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Shipment getShipment() {
    return shipment;
  }

  public void setShipment(Shipment shipment) {
    this.shipment = shipment;
  }

  public ShipmentStatus getOldStatus() {
    return oldStatus;
  }

  public void setOldStatus(ShipmentStatus oldStatus) {
    this.oldStatus = oldStatus;
  }

  public ShipmentStatus getNewStatus() {
    return newStatus;
  }

  public void setNewStatus(ShipmentStatus newStatus) {
    this.newStatus = newStatus;
  }
}
