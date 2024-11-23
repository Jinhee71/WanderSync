package com.example.sprintproject.model;

public class TravelPost {
    private String username;
    private String destination;
    private String duration;

    public TravelPost(String username, String destination, String duration) {
        this.username = username;
        this.destination = destination;
        this.duration = duration;
    }

    public String getUsername() {
        return username;
    }

    public String getDestination() {
        return destination;
    }

    public String getDuration() {
        return duration;
    }
}
