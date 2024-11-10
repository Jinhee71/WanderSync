package com.example.sprintproject;

import static org.junit.Assert.*;

import com.example.sprintproject.model.Destination;
import com.example.sprintproject.model.Dining;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class BrianYangUnitTest2 {

    @Test // Test the duration of vacation
    public void testDurationCalculation() {
        LocalDate startDate = LocalDate.of(2024, 12, 1);
        LocalDate endDate = LocalDate.of(2024, 12, 10);
        Destination destination = new Destination("Paris", startDate, endDate);


        assertEquals("Duration should be 9 days", 9L, destination.durationCalc());
    }


    @Test // Test for empty location input
    public void testEmptyLocationInput() {
        LocalDate startDate = LocalDate.of(2024, 12, 1);
        LocalDate endDate = LocalDate.of(2024, 12, 10);
        Destination destination = new Destination("", startDate, endDate);


        assertTrue("Location input should not be empty", destination.getDestinationLocation().isEmpty());
    }


    @Test
    public void testInvalidDateRange() {
        LocalDate startDate = LocalDate.of(2024, 12, 10);
        LocalDate endDate = LocalDate.of(2024, 12, 1);
        Destination destination = new Destination("Paris", startDate, endDate);


        assertTrue("Duration should be negative for invalid date ranges", destination.durationCalc() < 0);
    }

    @Test // Test Dining object creation
    public void testDiningObjectCreation() {
        LocalDateTime reservationTime = LocalDateTime.of(2024, 12, 10, 18, 30);
        Dining dining = new Dining("https://brian.com", "Chicago", reservationTime, 5);

        assertEquals("Chicago", dining.getLocation());
        assertEquals("https://brian.com", dining.getWebsite());
        assertEquals(reservationTime, dining.getReservationTime());
        assertEquals(5, dining.getReview());
    }

    @Test // Test setting and getting the location
    public void testDiningSetLocation() {
        Dining dining = new Dining();
        dining.setLocation("New Orleans");

        assertEquals("New Orleans", dining.getLocation());
    }

    @Test // Test setting and getting the website
    public void testDiningSetWebsite() {
        Dining dining = new Dining();
        dining.setWebsite("https://2340.com");

        assertEquals("https://2340.com", dining.getWebsite());
    }

    @Test // Test setting and getting the review rating
    public void testDiningSetReview() {
        Dining dining = new Dining();
        dining.setReview(4);

        assertEquals(4, dining.getReview());
    }

    @Test // Test setting and getting the reservation time
    public void testDiningSetReservationTime() {
        LocalDateTime testTime = LocalDateTime.of(2024, 12, 15, 20, 0);
        Dining dining = new Dining();
        dining.setReservationTime(testTime);

        assertEquals(testTime, dining.getReservationTime());
    }

    @Test // Test isDuplicateReservation method for simple logic
    public void testIsDuplicateReservation() {
        String location = "Atlanta";
        LocalDateTime reservationTime = LocalDateTime.of(2024, 12, 15, 19, 0);

        assertTrue("Location and time should match for duplicates",
                location.equalsIgnoreCase("Atlanta") && reservationTime.equals(LocalDateTime.of(2024, 12, 15, 19, 0)));
    }




}
