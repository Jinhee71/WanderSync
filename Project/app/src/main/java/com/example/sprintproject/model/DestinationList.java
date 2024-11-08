package com.example.sprintproject.model;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class DestinationList {
    private static DestinationList instance;
    private List<Destination> destinationList;

    // Private constructor to restrict instantiation
    private DestinationList() {
        destinationList = new ArrayList<>();
        // Prepopulate with at least 2 destinations as required
        destinationList.add(new Destination("Paris", LocalDate.of(2024, 10, 16),
                LocalDate.of(2024, 10, 22)));
        destinationList.add(new Destination("Tokyo", LocalDate.of(2025, 5, 1),
                LocalDate.of(2025, 5, 10)));
    }

    // Get the single instance of DestinationList
    public static DestinationList getInstance() {
        if (instance == null) {
            instance = new DestinationList();
        }
        return instance;
    }

    // Add a destination to the list
    public void addDestination(Destination destination) {
        destinationList.add(destination);
    }

    // Get the list of all destinations
    public List<Destination> getDestinations() {
        return destinationList;
    }

    public Destination findDestinationByName(String name) {
        for (Destination destination : destinationList) {
            if (destination.getDestinationLocation().equals(name)) {
                return destination;
            }
        }
        return null;
    }
}
