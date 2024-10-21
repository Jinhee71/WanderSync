package com.example.sprintproject.model;

import java.util.ArrayList;
import java.util.List;

public class Trip {
    private List<Destination> destinationList; // Multiple destinations in a trip
    private List<User> invitedUsers; // Users invited to contribute to the trip
    private List<Note> noteList;

    // Constructor
    public Trip() {
        destinationList = new ArrayList<>();
        invitedUsers = new ArrayList<>();
        noteList = new ArrayList<>();
    }

    // Add a destination to the trip
    public void addDestination(Destination destination) {
        destinationList.add(destination);
    }

    // Get all destinations in the trip
    public List<Destination> getDestinations() {
        return destinationList;
    }

    // Add a user to the invited list
    public void addInvitedUser(User user) {
        invitedUsers.add(user);
    }

    // Get the list of invited users
    public List<User> getInvitedUsers() {
        return invitedUsers;
    }


    // Add a destination to the trip
    public void addNote(Note note) {
        noteList.add(note);
    }

    // Get all destinations in the trip
    public List<Note> getNotes() {
        return noteList;
    }

    // Optional: Get total trip duration based on all destinations
    public long getTotalTripDuration() {
        long totalDays = 0;
        for (Destination destination : destinationList) {
            totalDays += destination.durationCalc(); // Assuming Destination has getTripDuration method
        }
        return totalDays;
    }

}
