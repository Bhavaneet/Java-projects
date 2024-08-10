package com.example.gui;

import com.example.model.Flight;
import com.example.model.FlightManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FlightManagementApp {
    private static FlightManager flightManager = new FlightManager();
    private static String currentUser = "User"; // You can set this dynamically based on login or user input

    public static void main(String[] args) {
        // Create some sample flights
        initializeFlights();

        // Create and set up the window
        JFrame frame = new JFrame("Flight Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLayout(new BorderLayout());

        // Create components
        JTextArea flightInfoArea = new JTextArea();
        flightInfoArea.setEditable(false);
        flightInfoArea.setLineWrap(true);
        flightInfoArea.setWrapStyleWord(true);
        flightInfoArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JComboBox<String> flightDropdown = new JComboBox<>();
        JButton showFlightButton = new JButton("Show Flight Info");
        JButton bookSeatButton = new JButton("Book Seat");
        JButton showHistoryButton = new JButton("Show Booking History");

        // Populate flight dropdown
        populateFlightDropdown(flightDropdown);

        // Layout for buttons and dropdown
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2, 1, 10, 10));
        JPanel dropdownPanel = new JPanel();
        dropdownPanel.add(new JLabel("Select Flight:"));
        dropdownPanel.add(flightDropdown);
        topPanel.add(dropdownPanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(showFlightButton);
        buttonPanel.add(bookSeatButton);
        buttonPanel.add(showHistoryButton);
        topPanel.add(buttonPanel);

        // Add components to the frame
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(flightInfoArea), BorderLayout.CENTER);

        // Add action listeners
        showFlightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String flightNumber = (String) flightDropdown.getSelectedItem();
                if (flightNumber != null) {
                    Flight flight = flightManager.getFlight(flightNumber);
                    if (flight != null) {
                        flightInfoArea.setText(flight.toString());
                    } else {
                        flightInfoArea.setText("Flight not found.");
                    }
                }
            }
        });

        bookSeatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String flightNumber = (String) flightDropdown.getSelectedItem();
                if (flightNumber != null) {
                    Flight flight = flightManager.getFlight(flightNumber);
                    if (flight != null) {
                        try {
                            flightManager.bookSeat(flightNumber, currentUser);
                            flightInfoArea.setText(flight.toString());
                            JOptionPane.showMessageDialog(frame, "Seat booked successfully.");
                        } catch (IllegalStateException ex) {
                            JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        flightInfoArea.setText("Flight not found.");
                    }
                }
            }
        });

        showHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> history = flightManager.getBookingHistory(currentUser);
                if (history.isEmpty()) {
                    flightInfoArea.setText("No booking history found.");
                } else {
                    StringBuilder historyText = new StringBuilder("Booking History:\n");
                    for (String flightNumber : history) {
                        Flight flight = flightManager.getFlight(flightNumber);
                        if (flight != null) {
                            historyText.append(flight.toString()).append("\n");
                        }
                    }
                    flightInfoArea.setText(historyText.toString());
                }
            }
        });

        // Display the window
        frame.setVisible(true);
    }

    private static void initializeFlights() {
        flightManager.addFlight(new Flight("AA123", "New York, USA", 50));
        flightManager.addFlight(new Flight("BA456", "London, UK", 30));
        flightManager.addFlight(new Flight("CA789", "Paris, France", 20));
        flightManager.addFlight(new Flight("DL234", "Los Angeles, USA", 40));
        flightManager.addFlight(new Flight("EK567", "Dubai, UAE", 25));
        flightManager.addFlight(new Flight("JL890", "Tokyo, Japan", 35));
        flightManager.addFlight(new Flight("QF123", "Sydney, Australia", 45));
        flightManager.addFlight(new Flight("LH456", "Berlin, Germany", 30));
        flightManager.addFlight(new Flight("AF789", "Rome, Italy", 20));
        flightManager.addFlight(new Flight("SU234", "Moscow, Russia", 50));
        flightManager.addFlight(new Flight("AC567", "Toronto, Canada", 40));
        flightManager.addFlight(new Flight("AZ890", "Rome, Italy", 25));
    }

    private static void populateFlightDropdown(JComboBox<String> flightDropdown) {
        List<Flight> flights = flightManager.getFlights();
        flightDropdown.removeAllItems(); // Clear existing items
        for (Flight flight : flights) {
            flightDropdown.addItem(flight.getFlightNumber());
        }
    }
}