package com.example.sprintproject.model;
import java.time.LocalDateTime;

public class Dining {
    private String location;
    private String website;
    private long review;
    private LocalDateTime reservationTime;

    public Dining() { }

    public Dining(String website, String location, LocalDateTime reservationTime, long review) {
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

    public long getReview() {
        return review;
    }
    public void setReview(long review) {
        this.review = review;
    }

    public LocalDateTime getReservationTime() {
        return reservationTime;
    }
    public void setReservationTime(LocalDateTime reservationTime) {
        this.reservationTime = reservationTime;
    }
}



