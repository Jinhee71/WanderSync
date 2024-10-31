package com.example.sprintproject.model;

public class Accommodation {
    private long checkInTime;
    private long checkOutTime;
    private int numRooms;
    private String roomType;

    public Accommodation(long checkInTime, long checkOutTime, int numRooms, String roomType) {
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.numRooms = numRooms;
        this.roomType = roomType;
    }

    public long getCheckInTime() {
        return checkInTime;
    }
    public void setCheckInTime(long checkInTime) {
        this.checkInTime = checkInTime;
    }

    public long getCheckOutTime() {
        return checkOutTime;
    }
    public void setCheckOutTime(long checkOutTime) {
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
