package com.filipi.services;

import com.filipi.entities.ParkSpots;
import com.filipi.exceptions.VehicleNotFoundException;
import com.filipi.interfaces.IParkSpotRepository;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

public class CalcPayment {
    private final int PRICE_PER_HOUR = 200; // price in centavos
    private final IParkSpotRepository repository;

    public CalcPayment(IParkSpotRepository repository) {
        this.repository = repository;
    }

    public long execute(String vehiclePlate) throws VehicleNotFoundException, IOException {
        ParkSpots parkSpot = this.repository.findByVehiclePlate(vehiclePlate);
        if (parkSpot == null) {
            throw new VehicleNotFoundException();
        }

        LocalDateTime start = parkSpot.getStartAt();
        LocalDateTime end = parkSpot.getEndAt();
        Duration duration = Duration.between(start, end);
        long durationMinutes = duration.toMinutes();

        if (durationMinutes <= 0) return 0;

        if (durationMinutes < 60) {
            return this.ceil(durationMinutes);
        }

        long totalHours = durationMinutes / 60;
        long minutesRemaining = durationMinutes % 60;
        long total = totalHours * this.PRICE_PER_HOUR;

        if (minutesRemaining > 0) {
            total += this.ceil(minutesRemaining);
        }

        return total;
    }

    private long ceil(long minutes) {
        int hour = 60;
        return (long) Math.ceil((this.PRICE_PER_HOUR * minutes) / hour);
    }
}
