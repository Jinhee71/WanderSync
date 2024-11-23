package com.example.sprintproject.model;

public class TravelCommunity {
    private long duration;
    private String destination;
    private String accommodation;
    private String dining;
    private String transportation;
    private String notes;

    public TravelCommunity() {}

    public TravelCommunity(long duration, String destination,
                           String accommodation, String dining,
                           String transportation, String notes) {
        this.duration = duration;
        this.destination = destination;
        this.accommodation = accommodation;
        this.dining = dining;
        this.transportation = transportation;
        this.notes = notes;
    }

    public TravelCommunity(long duration, String destination,
                           String accommodation, String dining,
                           String notes) {
        this.duration = duration;
        this.destination = destination;
        this.accommodation = accommodation;
        this.dining = dining;
        this.notes = notes;
    }

    public long getDuration() {
        return this.duration;
    }

    public void setDuration(long duration) {
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
}
