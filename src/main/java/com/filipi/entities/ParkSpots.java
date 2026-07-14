package com.filipi.entities;

import com.filipi.enums.VehicleType;

import java.time.LocalDateTime;

public class ParkSpots {
    private String id;
    private String vehiclePlate;
    private VehicleType vehicleType;
    private String vehicleName;
    private LocalDateTime startAt;
    private LocalDateTime endAt;

    public ParkSpots() {} // construtor vazio exigido pelo Jackson

    public ParkSpots(
            String vehiclePlate,
            VehicleType vehicleType,
            String vehicleName,
            LocalDateTime startAt,
            LocalDateTime endAt
    ) {
        this.id = vehiclePlate;
        this.vehiclePlate = vehiclePlate;
        this.vehicleType = vehicleType;
        this.vehicleName = vehicleName;
        this.startAt = startAt != null ? startAt : LocalDateTime.now();
        this.endAt = endAt;
    }

    public String getId() {
        return this.id;
    }

    public String getVehiclePlate() {
        return vehiclePlate;
    }

    public void setVehiclePlate(String vehiclePlate) {
        this.vehiclePlate = vehiclePlate;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public LocalDateTime getStartAt() {
        return startAt;
    }

    public void setStartAt(LocalDateTime startAt) {
        this.startAt = startAt;
    }

    public LocalDateTime getEndAt() {
        return endAt;
    }

    public void setEndAt(LocalDateTime endAt) {
        this.endAt = endAt;
    }
}
