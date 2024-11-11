package com.example.sprintproject;

import static org.junit.Assert.*;

import com.example.sprintproject.model.Accommodation;
import com.example.sprintproject.model.Destination;
import com.example.sprintproject.model.Dining;
import com.example.sprintproject.model.User;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class JinheeLeeUnitTest {

    @Test
    public void testDurationCalculation() {
        //Testing duration of vacation
        LocalDate startDate = LocalDate.of(2020, 6, 13);
        LocalDate endDate = LocalDate.of(2020, 6, 20);
        Destination destination = new Destination("Miami", startDate, endDate);

        assertEquals("Duration should be 7 days", 7L, destination.durationCalc());
    }

    @Test
    public void testUserDurationCalculation() {
        LocalDate startDate = LocalDate.of(2020, 6, 13);
        LocalDate endDate = LocalDate.of(2020, 6, 20);
        User user = new User();

        user.setStartDate(startDate);
        user.setEndDate(endDate);

        user.calculateMissingValues();
        assertEquals(user.getDuration(), 7);
    }

    @Test
    public void testUserStartDateCalculation() {
        LocalDate startDate = LocalDate.of(2020, 6, 13);
        LocalDate endDate = LocalDate.of(2020, 6, 20);
        User user = new User();

        user.setDuration(7);
        user.setEndDate(endDate);

        user.calculateMissingValues();
        assertEquals(user.getStartDate(), startDate);
    }

    @Test
    public void testUserEndDateCalculation() {
        LocalDate startDate = LocalDate.of(2020, 6, 13);
        LocalDate endDate = LocalDate.of(2020, 6, 20);
        User user = new User();

        user.setStartDate(startDate);
        user.setDuration(7);

        user.calculateMissingValues();
        assertEquals(user.getEndDate(), endDate);
    }

    @Test
    public void testEmptyLocationInput() {
        //Testing for empty location input
        LocalDate startDate = LocalDate.of(2020, 6, 13);
        LocalDate endDate = LocalDate.of(2020, 6, 20);
        Destination destination = new Destination("", startDate, endDate);

        assertTrue("Location input should not be empty", destination.getDestinationLocation().isEmpty());
    }

    @Test
    public void testInvalidDateRange() {
        //Testing for invalid date range
        LocalDate startDate = LocalDate.of(2020, 6, 27);
        LocalDate endDate = LocalDate.of(2020, 6, 20);
        Destination destination = new Destination("Miami", startDate, endDate);

        assertTrue("Duration should be negative for invalid date ranges", destination.durationCalc() < 0);
    }

    @Test // Test setting and getting the review rating
    public void testDiningSetReview() {
        Dining dining = new Dining();
        dining.setReview(4);

        assertEquals(4, dining.getReview());
    }

    @Test // Test setting and getting the check-out time
    public void testSetCheckOut() {
        String checkOut = "5:00";
        Accommodation accommodation = new Accommodation();
        accommodation.setCheckOut(checkOut);

        assertEquals(checkOut, accommodation.getCheckOut());
    }

}
