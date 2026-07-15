package com.filipi.services;

import com.filipi.dtos.CheckinInput;
import com.filipi.entities.Ticket;
import com.filipi.exceptions.TicketStartedException;
import com.filipi.interfaces.ITicketRepository;

import java.io.IOException;

public class Checkin {
    private final ITicketRepository repository;

    public Checkin(ITicketRepository repository) {
        this.repository = repository;
    }

    public void execute(CheckinInput input) throws TicketStartedException, IOException {
        Ticket ticket = this.repository.findByVehiclePlate(input.vehiclePlate());
        if (ticket != null) {
            throw new TicketStartedException();
        }
        this.repository.save(input);
    }
}
