package com.example.sprintproject.model;
import java.time.LocalDateTime;


public class Accommodation {
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    private int numRooms;
    private String roomType;

    public Accommodation() {
    }

    public Accommodation(LocalDateTime checkInTime, LocalDateTime checkOutTime, int numRooms, String roomType) {

        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.numRooms = numRooms;
        this.roomType = roomType;
    }

    public LocalDateTime getCheckInTime() {
        return checkInTime;
    }
    public void setCheckInTime(LocalDateTime checkInTime) {
        this.checkInTime = checkInTime;
    }

    public LocalDateTime getCheckOutTime() {
        return checkOutTime;
    }
    public void setCheckOutTime(LocalDateTime checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public int getNumRooms() {
        return numRooms;
    }
    public void setNumRooms(int numRooms) {
        this.numRooms = numRooms;
    }

    public String getRoomType() {
        return roomType;
    }
    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }
}
