package com.example.model;

public class Flight {
    private String flightNumber;
    private String destination;
    private int availableSeats;

    public Flight(String flightNumber, String destination, int availableSeats) {
        this.flightNumber = flightNumber;
        this.destination = destination;
        this.availableSeats = availableSeats;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getDestination() {
        return destination;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void bookSeat() {
        if (availableSeats > 0) {
            availableSeats--;
        } else {
            throw new IllegalStateException("No seats available");
        }
    }

    @Override
    public String toString() {
        return flightNumber + " to " + destination + " (Seats available: " + availableSeats + ")";
    }
}