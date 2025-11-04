package com.udea.couriersync.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "Vehicle")
public class Vehicle {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String plate;

  @Column(nullable = false)
  private String model;

  @Column(name = "maximum_capacity", nullable = false)
  private Double maximumCapacity;

  @Column(nullable = false)
  private boolean available;

  public Vehicle() {
  }

  @JsonCreator
  public Vehicle(@JsonProperty("plate") String plate,
      @JsonProperty("model") String model,
      @JsonProperty("maximumCapacity") Double maximumCapacity,
      @JsonProperty("available") boolean available) {
    this.plate = plate;
    this.model = model;
    this.maximumCapacity = maximumCapacity;
    this.available = available;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getPlate() {
    return plate;
  }

  public void setPlate(String plate) {
    this.plate = plate;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public Double getMaximumCapacity() {
    return maximumCapacity;
  }

  public void setMaximumCapacity(Double maximumCapacity) {
    this.maximumCapacity = maximumCapacity;
  }

  public boolean isAvailable() {
    return available;
  }

  public void setAvailable(boolean available) {
    this.available = available;
  }
}
