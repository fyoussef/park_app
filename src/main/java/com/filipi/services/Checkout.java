package com.filipi.services;

import com.filipi.entities.Ticket;
import com.filipi.exceptions.VehicleNotFoundException;
import com.filipi.interfaces.ITicketRepository;

import java.io.IOException;
import java.time.LocalDateTime;

public class Checkout {
    private final ITicketRepository repository;

    public Checkout(ITicketRepository repository) {
        this.repository = repository;
    }

    public void execute(String vehiclePlate) throws VehicleNotFoundException, IOException {
        Ticket ticket = this.repository.findByVehiclePlate(vehiclePlate);

        if (ticket == null) {
            throw new VehicleNotFoundException();
        }

        ticket.setEndAt(LocalDateTime.now());
        this.repository.update(ticket);
    }
}
