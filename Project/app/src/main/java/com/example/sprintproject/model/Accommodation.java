package com.example.sprintproject.model;

import java.time.LocalDateTime;

public class Accommodation {
    private String location;
    private String hotelName;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private int numberOfRooms;
    private String roomType;

    public Accommodation () {

    }

    public Accommodation(String location, String hotelName, LocalDateTime checkIn, LocalDateTime checkOut, int numberOfRooms, String roomType) {
        this.location = location;
        this.hotelName = hotelName;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.numberOfRooms = numberOfRooms;
        this.roomType = roomType;
    }

    //Getters and Setters
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getHotelName() { return hotelName; }
    public void setHotelName(String hotelName) { this.hotelName = hotelName; }

    public LocalDateTime getCheckIn() { return checkIn; }
    public void setCheckIn(LocalDateTime checkIn) { this.checkIn = checkIn; }

    public LocalDateTime getCheckOut() { return checkOut; }
    public void setCheckOut(LocalDateTime checkOut) { this.checkOut = checkOut; }

    public int getNumberOfRooms() { return numberOfRooms; }
    public void setNumberOfRooms(int numberOfRooms) { this.numberOfRooms = numberOfRooms; }

    public String getRoomType() { return roomType; }
    public void setRoomType(String roomType) { this.roomType = roomType; }
}