package com.example.sprintproject.model;

import java.time.LocalDateTime;
import java.util.List;

public class TravelCommunity {
    private String duration;
    private List<String> destination;
    private List<String> accommodation;
    private List<String> dining;
    private List<String> transportation;
    private String notes;
    private long timestamp;  // Add a timestamp field

    public TravelCommunity() {}

    public TravelCommunity(String duration, List<String> destination,
                           List<String> accommodation, List<String> dining,
                           List<String> transportation, String notes, long timestamp) {
        this.duration = duration;
        this.destination = destination;
        this.accommodation = accommodation;
        this.dining = dining;
        this.transportation = transportation;
        this.notes = notes;
        this.timestamp = timestamp;  // Initialize timestamp
    }

    // Getter and Setter methods
    public String getDuration() {
        return this.duration;
    }
    public void setDuration(String duration) {
        this.duration = duration;
    }

    public List<String> getDestination() {
        return this.destination;
    }
    public void setDestination(List<String> destination) {
        this.destination = destination;
    }

    public List<String> getAccommodation() {
        return this.accommodation;
    }
    public void setAccommodation(List<String> accommodation) {
        this.accommodation = accommodation;
    }

    public List<String> getDining() {
        return this.dining;
    }
    public void setDining(List<String> dining) {
        this.dining = dining;
    }

    public List<String> getTransportation() {
        return this.transportation;
    }
    public void setTransportation(List<String> transportation) {
        this.transportation = transportation;
    }

    public String getNotes() {
        return this.notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }

    public long getTimestamp() {
        return this.timestamp;
    }
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
