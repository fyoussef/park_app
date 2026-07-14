package com.filipi.exceptions;

public class VehicleParkedException extends Exception {
    public VehicleParkedException() {
        super("Vehicle is already parked");
    }
}
