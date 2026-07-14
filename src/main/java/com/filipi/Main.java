package com.filipi;

import com.filipi.dtos.CheckinInput;
import com.filipi.dtos.CheckoutResponse;
import com.filipi.dtos.HTTPResponse;
import com.filipi.enums.VehicleType;
import com.filipi.exceptions.VehicleNotFoundException;
import com.filipi.exceptions.VehicleParkedException;
import com.filipi.interfaces.IParkSpotRepository;
import com.filipi.repository.ParkSpotRepository;
import com.filipi.services.CalcPayment;
import com.filipi.services.Checkin;
import com.filipi.services.Checkout;
import io.javalin.Javalin;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.NotFoundResponse;
import io.javalin.validation.ValidationException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        IParkSpotRepository repository = new ParkSpotRepository();

        Checkin checkin = new Checkin(repository);
        Checkout checkout = new Checkout(repository);
        CalcPayment calcPayment = new CalcPayment(repository);

        var app = Javalin.create(config -> {
            config.routes.get("/", ctx -> {
                ctx.json(new HTTPResponse(true, "Running!", null));
            });

            config.routes.post("/checkin", ctx -> {

                CheckinInput body = ctx.bodyValidator(CheckinInput.class)
                        .check(payload -> payload.vehiclePlate() != null, "A placa não foi informada")
                        .check(payload -> payload.vehicleName() != null && (payload.vehicleName().isEmpty() || payload.vehicleName().length() > 2), "O nome do veículo é obrigatório")
                        .check(payload -> payload.vehicleType() != null && VehicleType.verify(payload.vehicleType().toString()), "O tipo de veículo está inválido")
                        .get();

                try {
                    checkin.execute(body);
                    ctx.json(new HTTPResponse(true, String.format("Entrada do veículo %s com a placa %s foi feito", body.vehicleName(), body.vehiclePlate()), null));
                } catch (Exception e) {
                    if (e instanceof VehicleParkedException) {
                        throw new BadRequestResponse(e.getMessage());
                    }
                    throw  e;
                }
            });

            config.routes.put("/checkout/{vehicle_plate}", ctx -> {
                String vehiclePlate = ctx.pathParam("vehicle_plate");

                try {
                    checkout.execute(vehiclePlate);
                    long total = calcPayment.execute(vehiclePlate);

                    ctx.json(new HTTPResponse(true, "Veículo de saída", new CheckoutResponse(total / 100)));
                } catch (Exception e) {
                    if (e instanceof VehicleNotFoundException) {
                        throw new NotFoundResponse(e.getMessage());
                    }
                    throw e;
                }
            });

            config.routes.exception(ValidationException.class, (e, ctx) -> {
                ctx.json(e.getErrors()).status(400);
            });

        }).start(7070);
    }
}