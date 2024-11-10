package com.example.sprintproject.model;

public class Accommodation {

    private String location;
    private String hotelName;
    private String checkIn;
    private String checkOut;
    private int numberOfRooms;
    private String roomType;

    // No-argument constructor (required by Firestore)
    public Accommodation() {
        // Firestore needs this constructor for deserialization
    }

    // Constructor
    public Accommodation(String location, String hotelName, String checkIn, String checkOut, int numberOfRooms, String roomType) {
        this.location = location;
        this.hotelName = hotelName;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.numberOfRooms = numberOfRooms;
        this.roomType = roomType;
    }

    // Getters
    public String getLocation() {
        return location;
    }

    public String getHotelName() {
        return hotelName;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public String getRoomType() {
        return roomType;
    }

    // Setters (if necessary)
    public void setLocation(String location) {
        this.location = location;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }
}
