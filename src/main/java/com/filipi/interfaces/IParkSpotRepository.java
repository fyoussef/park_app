package com.filipi.interfaces;

import com.filipi.dtos.CheckinInput;
import com.filipi.entities.ParkSpots;

import java.io.IOException;

public interface IParkSpotRepository {
    void save(CheckinInput input) throws IOException;
    ParkSpots findByVehiclePlate(String vehiclePlate) throws IOException;
    void update(ParkSpots parkSpot) throws IOException;
}
