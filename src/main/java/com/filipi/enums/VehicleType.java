package com.filipi.enums;

public enum VehicleType {
    CAR, MOTORCYLE, TRUCK, PICKUP_TRUCK, BUS, MINIBUS;

    public static boolean verify(String vehicleType) {
        boolean result = false;
        for (VehicleType type : VehicleType.values()) {
            if (type.name().equals(vehicleType)) {
                result = true;
                break;
            };
        }
        return result;
    }
}
