package com.udea.couriersync.dto;

public class VehicleDTO {
  private Long id;
  private String plate;
  private String model;
  private Double maximumCapacity;
  private boolean available;

  public VehicleDTO() {
  }

  public VehicleDTO(Long id, String plate, String model, Double maximumCapacity, boolean available) {
    this.id = id;
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
