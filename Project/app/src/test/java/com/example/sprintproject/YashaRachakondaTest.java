package com.example.sprintproject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.example.sprintproject.model.Destination;
import com.example.sprintproject.model.User;

import org.junit.Test;

import java.time.LocalDate;

public class YashaRachakondaTest {
    @Test // Test the duration of vacation
    public void testDurationCalculation() {
        LocalDate startDate = LocalDate.of(2011, 05, 05);
        LocalDate endDate = LocalDate.of(2011, 05, 30);
        Destination destination = new Destination("Paris", startDate, endDate);


        assertEquals("Duration should be 9 days", 25L, destination.durationCalc());
    }


    @Test // Test for empty location input
    public void testEmptyLocationInput() {
        LocalDate startDate = LocalDate.of(2012, 11, 1);
        LocalDate endDate = LocalDate.of(2012, 11, 10);
        Destination destination = new Destination("", startDate, endDate);


        assertTrue("Location input should not be empty", destination.getDestinationLocation().isEmpty());
    }

    @Test
    public void testUserDurationCalculation() {
        LocalDate startDate = LocalDate.of(2005, 06, 13);
        LocalDate endDate = LocalDate.of(2005, 06, 20);
        User user = new User();

        user.setStartDate(startDate);
        user.setEndDate(endDate);

        user.calculateMissingValues();
        assertEquals(user.getDuration(), 7);
    }

}
