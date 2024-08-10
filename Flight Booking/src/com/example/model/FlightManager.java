package com.example.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlightManager {
    private List<Flight> flights = new ArrayList<>();
    private Map<String, List<String>> bookingHistory = new HashMap<>();

    public void addFlight(Flight flight) {
        flights.add(flight);
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public Flight getFlight(String flightNumber) {
        for (Flight flight : flights) {
            if (flight.getFlightNumber().equals(flightNumber)) {
                return flight;
            }
        }
        return null;
    }

    public void bookSeat(String flightNumber, String user) {
        Flight flight = getFlight(flightNumber);
        if (flight != null) {
            flight.bookSeat();
            addBookingHistory(user, flightNumber);
        }
    }

    private void addBookingHistory(String user, String flightNumber) {
        bookingHistory.computeIfAbsent(user, k -> new ArrayList<>()).add(flightNumber);
    }

    public List<String> getBookingHistory(String user) {
        return bookingHistory.getOrDefault(user, new ArrayList<>());
    }
}
