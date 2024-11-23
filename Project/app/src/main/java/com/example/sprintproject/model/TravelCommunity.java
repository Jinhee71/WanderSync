package com.example.sprintproject.model;

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

    public String getDestination() {
        return this.destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getAccommodation() {
        return this.accommodation;
    }

    public void setAccommodation(String accommodation) {
        this.accommodation = accommodation;
    }

    public String getDining() {
        return this.dining;
    }

    public void setDining(String dining) {
        this.dining = dining;
    }

    public String getTransportation() {
        return this.transportation;
    }

    public void setTransportation(String transportation) {
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
