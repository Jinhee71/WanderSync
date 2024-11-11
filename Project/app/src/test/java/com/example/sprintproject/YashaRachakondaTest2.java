package com.example.sprintproject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.example.sprintproject.model.Destination;
import com.example.sprintproject.model.Dining;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class YashaRachakondaTest2 {

    @Test // Test the duration of vacation
    public void testDurationCalculation() {
        LocalDate startDate = LocalDate.of(2024, 12, 2);
        LocalDate endDate = LocalDate.of(2024, 12, 10);
        Destination destination = new Destination("Paris", startDate, endDate);


        assertEquals("Duration should be 8 days", 8L, destination.durationCalc());
    }

    @Test
    public void testDiningObjectCreation() {
        // Test data
        String expectedWebsite = "http://example.com";
        String expectedLocation = "Test Location";
        LocalDateTime expectedReservationTime = LocalDateTime.of(2024, 11, 15, 18, 30);
        long expectedReview = 5;

        // Create Dining object with parameters
        Dining dining = new Dining(expectedWebsite, expectedLocation, expectedReservationTime, expectedReview);

        // Verify that the object is not null
        assertNotNull(dining);

        // Verify that all parameters were set correctly
        assertEquals(expectedWebsite, dining.getWebsite());
        assertEquals(expectedLocation, dining.getLocation());
        assertEquals(expectedReservationTime, dining.getReservationTime());
        assertEquals(expectedReview, dining.getReview());
    }
}


