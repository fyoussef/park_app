package com.filipi.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.filipi.dtos.CheckinInput;
import com.filipi.entities.Ticket;
import com.filipi.interfaces.ITicketRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class TicketRepository implements ITicketRepository {
    private Path path = Path.of(
            "C:\\Users\\fyoussef\\Desktop\\java\\park_app\\src\\main\\java\\com\\filipi\\repository\\database.json"
    );
    private File database = this.path.toFile();
    private ObjectMapper mapper = new ObjectMapper();


    private HashMap<String, Ticket> loadAll() throws IOException {
        this.mapper.registerModule(new JavaTimeModule());

        if (this.database.length() == 0) {
            return new HashMap<>();
        }

        return this.mapper.readValue(
                this.database,
                new TypeReference<HashMap<String, Ticket>>() {}
        );
    }

    public void save(CheckinInput input) throws IOException {
        this.mapper.registerModule(new JavaTimeModule());

        HashMap<String, Ticket> parkSpots = this.loadAll();
        Ticket parkSpot = new Ticket(input.vehiclePlate(), input.vehicleType(), input.vehicleName(), input.startAt(), null);
        parkSpots.put(parkSpot.getId(), parkSpot);

        String data = this.mapper.writeValueAsString(parkSpots);
        Files.writeString(
                this.path,
                data
        );
    }

    public Ticket findByVehiclePlate(String vehiclePlate) throws IOException {
        this.mapper.registerModule(new JavaTimeModule());

        HashMap<String, Ticket> parkSpots = this.loadAll();
        System.out.println(parkSpots instanceof HashMap<String, Ticket>);
        return parkSpots.get(vehiclePlate);
    }

    public void update(Ticket parkSpot) throws IOException {
        this.mapper.registerModule(new JavaTimeModule());

        HashMap<String, Ticket> parkSpots = this.loadAll();
        parkSpots.replace(parkSpot.getId(), parkSpot);

        String data = this.mapper.writeValueAsString(parkSpots);
        Files.writeString(
                this.path,
                data
        );
    }
}
