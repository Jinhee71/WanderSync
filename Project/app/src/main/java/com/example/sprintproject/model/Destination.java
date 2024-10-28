package com.example.sprintproject.model;
import java.time.LocalDate;

public class Destination {
    private String destinationLocation;
    private LocalDate startDate;
    private LocalDate endDate;

    public Destination(String destinationLocation, LocalDate startDate, LocalDate endDate) {
        this.destinationLocation = destinationLocation;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getDestinationLocation() {
        return destinationLocation;
    }

    public void setDestinationLocation(String destinationLocation) {
        this.destinationLocation = destinationLocation;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public long durationCalc() {
        return java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate);
    }

}
