package com.filipi.interfaces;

import com.filipi.dtos.CheckinInput;
import com.filipi.entities.Ticket;

import java.io.IOException;

public interface ITicketRepository {
    void save(CheckinInput input) throws IOException;
    Ticket findByVehiclePlate(String vehiclePlate) throws IOException;
    void update(Ticket parkSpot) throws IOException;
}
