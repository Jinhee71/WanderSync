package com.example.sprintproject.model;
import java.time.LocalDate;


public class User {
    private String username;
    private String password;
    private LocalDate startDate;
    private LocalDate endDate;
    private long duration;


    //Constructor
    public User(String username, String password, LocalDate startDate,
                LocalDate endDate, long duration) {
        this.username = username;
        this.password = password;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = duration;
    }

    public User() {
        this.username = null;
        this.password = null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
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

    public void calculateMissingValues() {
        if (startDate != null && endDate != null && duration == 0) {
            // Calculate duration if start and end dates are available
            duration = java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate);
        } else if (startDate != null && duration != 0 && endDate == null) {
            // Calculate end date if start date and duration are available
            endDate = startDate.plusDays(duration);
        } else if (endDate != null && duration != 0 && startDate == null) {
            // Calculate start date if end date and duration are available
            startDate = endDate.minusDays(duration);
        }
    }
}
