package com.udea.couriersync.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.udea.couriersync.entity.Shipment;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
  java.util.Optional<Shipment> findByTrackingCode(String trackingCode);
}