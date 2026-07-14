package com.filipi.services;

import com.filipi.dtos.CheckinInput;
import com.filipi.entities.ParkSpots;
import com.filipi.exceptions.VehicleParkedException;
import com.filipi.interfaces.IParkSpotRepository;

import java.io.IOException;

public class Checkin {
    private final IParkSpotRepository repository;

    public Checkin(IParkSpotRepository repository) {
        this.repository = repository;
    }

    public void execute(CheckinInput input) throws VehicleParkedException, IOException {
        ParkSpots parkSpots = this.repository.findByVehiclePlate(input.vehiclePlate());
        if (parkSpots != null) {
            throw new VehicleParkedException();
        }
        this.repository.save(input);
    }
}
