package com.filipi.services;

import com.filipi.entities.ParkSpots;
import com.filipi.exceptions.VehicleNotFoundException;
import com.filipi.interfaces.IParkSpotRepository;

import java.io.IOException;
import java.time.LocalDateTime;

public class Checkout {
    private final IParkSpotRepository repository;

    public Checkout(IParkSpotRepository repository) {
        this.repository = repository;
    }

    public void execute(String vehiclePlate) throws VehicleNotFoundException, IOException {
        ParkSpots parkSpot = this.repository.findByVehiclePlate(vehiclePlate);

        if (parkSpot == null) {
            throw new VehicleNotFoundException();
        }

        parkSpot.setEndAt(LocalDateTime.now());
        this.repository.update(parkSpot);
    }
}
