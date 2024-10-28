package com.example.sprintproject;



import static org.junit.Assert.*;



import com.example.sprintproject.model.Destination;



import org.junit.Test;


import java.time.LocalDate;


public class HyeonjaeUnitTest2 {

    @Test // Test the duration of vacation
    public void testDurationCalculation() {
        LocalDate startDate = LocalDate.of(2024, 1, 3);
        LocalDate endDate = LocalDate.of(2024, 1, 10);
        Destination destination = new Destination("Seoul", startDate, endDate);


        assertEquals("Duration should be 7 days", 7L, destination.durationCalc());
    }


    @Test // Test for empty location input
    public void testEmptyLocationInput() {
        LocalDate startDate = LocalDate.of(2024, 1, 3);
        LocalDate endDate = LocalDate.of(2024, 1, 10);
        Destination destination = new Destination("", startDate, endDate);


        assertTrue("Location input should not be empty", destination.getDestinationLocation().isEmpty());
    }


    @Test
    public void testInvalidDateRange() {
        LocalDate startDate = LocalDate.of(2024, 1, 10);
        LocalDate endDate = LocalDate.of(2024, 1, 3);
        Destination destination = new Destination("Paris", startDate, endDate);


        assertTrue("Duration should be negative for invalid date ranges", destination.durationCalc() < 0);
    }



}