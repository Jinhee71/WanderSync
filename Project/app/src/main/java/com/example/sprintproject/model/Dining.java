package com.example.sprintproject.model;

public class Dining {
    private String location;
    private String website;
    private String review;
    private long reservationTime;

    public Dining(String website, String location, long reservationTime, String review) {
        this.website = website;
        this.location = location;
        this.reservationTime = reservationTime;
        this.review =  review;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public String getWebsite() {
        return website;
    }
    public void setWebsite(String website) {
        this.website = website;
    }

    public String getReview() {
        return review;
    }
    public void setReview(String review) {
        this.review = review;
    }

    public long getReservationTime() {
        return reservationTime;
    }
    public void setReservationTime(long reservationTime) {
        this.reservationTime = reservationTime;
    }
}



