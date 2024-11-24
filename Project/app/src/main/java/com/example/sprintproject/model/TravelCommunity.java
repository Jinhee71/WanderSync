package com.example.sprintproject.model;

public class TravelCommunity {
    private long duration;
    private String destination;
    private String accommodations;
    private String diningReservations;
    private String notes;
    private long timestamp;
    private String username;

    public TravelCommunity() {
        this.timestamp = System.currentTimeMillis();
    }

    public TravelCommunity(long duration, String destination, String accommodations,
                           String diningReservations, String notes, String username) {
        this.duration = duration;
        this.destination = destination;
        this.accommodations = accommodations;
        this.diningReservations = diningReservations;
        this.notes = notes;
        this.username = username;
        this.timestamp = System.currentTimeMillis();
    }

    // Getters and Setters
    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getAccommodations() {
        return accommodations;
    }

    public void setAccommodations(String accommodations) {
        this.accommodations = accommodations;
    }

    public String getDiningReservations() {
        return diningReservations;
    }

    public void setDiningReservations(String diningReservations) {
        this.diningReservations = diningReservations;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}