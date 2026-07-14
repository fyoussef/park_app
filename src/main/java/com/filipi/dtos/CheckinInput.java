package com.filipi.dtos;

import com.filipi.enums.VehicleType;

import java.time.LocalDateTime;

public record CheckinInput(
        String vehiclePlate,
        VehicleType vehicleType,
        String vehicleName,
        LocalDateTime startAt
) {
}
