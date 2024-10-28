package com.example.sprintproject;
import static org.junit.Assert.*;

import com.example.sprintproject.model.Destination;

import org.junit.Test;

import java.time.LocalDate;

public class HyeonjaeUnitTest1 {
    @Test
    public void testDurationCalculation() {
    LocalDate startDate = LocalDate.of(2024, 2, 1);
    LocalDate endDate = LocalDate.of(2024, 2, 10);
    Destination destination = new Destination("Paris", startDate, endDate);


    assertEquals("Duration should be 9 days", 9L, destination.durationCalc());
}


    @Test // Test for empty location input
    public void testEmptyLocationInput() {
        LocalDate startDate = LocalDate.of(2024, 2, 1);
        LocalDate endDate = LocalDate.of(2024, 2, 10);
        Destination destination = new Destination("", startDate, endDate);


        assertTrue("Location input should not be empty", destination.getDestinationLocation().isEmpty());
    }
}