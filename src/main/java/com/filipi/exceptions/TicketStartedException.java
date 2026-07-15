package com.filipi.exceptions;

public class TicketStartedException extends Exception {
    public TicketStartedException() {
        super("Vehicle is already parked");
    }
}
