package com.example.sprintproject.model;

import java.util.List;

public class Trip {

    private String tripName;
    private String destination;
    private List<Accommodation> accommodations;  // List of accommodations

    // Default constructor for Firestore
    public Trip() { }

    public Trip(String tripName, String destination, List<Accommodation> accommodations) {
        this.tripName = tripName;
        this.destination = destination;
        this.accommodations = accommodations;
    }

    // Getters and setters
    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public List<Accommodation> getAccommodations() {
        return accommodations;
    }

    public void setAccommodations(List<Accommodation> accommodations) {
        this.accommodations = accommodations;
    }
}
