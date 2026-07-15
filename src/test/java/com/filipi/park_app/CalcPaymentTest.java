package com.filipi.park_app;

import com.filipi.entities.ParkSpots;
import com.filipi.entities.Ticket;
import com.filipi.enums.VehicleType;
import com.filipi.interfaces.ITicketRepository;
import com.filipi.services.CalcPayment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CalcPaymentTest {
    @Mock
    private ITicketRepository repository;

    @Test
    public void should_calc_parking_price_less_than_hour() throws IOException {
        String vehiclePlate = "TEST_PLATE_123";
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusMinutes(30);

        Ticket ticket = new Ticket(vehiclePlate, VehicleType.CAR, "Tracker", start, end);

        when(this.repository.findByVehiclePlate(vehiclePlate)).thenReturn(parkSpots);

        CalcPayment sut = new CalcPayment(this.repository);

        long price = sut.execute(vehiclePlate);

        assertEquals(100, price);
        verify(this.repository).findByVehiclePlate(vehiclePlate);
    }

    @Test
    public void should_calc_parking_price_more_than_hour() throws IOException {
        String vehiclePlate = "TEST_PLATE_123";
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusMinutes(90);

        Ticket ticket = new Ticket(vehiclePlate, VehicleType.CAR, "Tracker", start, end);
        when(this.repository.findByVehiclePlate(vehiclePlate)).thenReturn(ticket);

        CalcPayment sut = new CalcPayment(this.repository);

        long price = sut.execute(vehiclePlate);

        assertEquals(300, price);
        verify(this.repository).findByVehiclePlate(vehiclePlate);
    }
}
