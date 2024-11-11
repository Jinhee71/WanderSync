package com.example.sprintproject;



import static org.junit.Assert.*;


import com.example.sprintproject.model.Accommodation;
import com.example.sprintproject.model.Destination;
import com.example.sprintproject.model.Trip;
import com.example.sprintproject.model.User;


import org.junit.Before;
import org.junit.Test;


import java.time.LocalDate;


public class JosephSeoUnitTest {
    private Trip trip;
    private Destination destination;
    private User user;

    @Before
    public void setUp() {
        trip = new Trip();
        destination = new Destination("Paris", LocalDate.now(), LocalDate.now().plusDays(5)); // Create a Destination
        user = new User();
    }

    @Test
    public void testInvalidDateRange() {
        LocalDate startDate = LocalDate.of(2024, 12, 10);
        LocalDate endDate = LocalDate.of(2024, 12, 1);
        Destination destination = new Destination("Paris", startDate, endDate);


        assertTrue("Duration should be negative for invalid date ranges", destination.durationCalc() < 0);
    }

    @Test // Test setting and getting the room type
    public void testSetRoomType() {
        Accommodation accommodation = new Accommodation();
        accommodation.setRoomType("Deluxe");

        assertEquals("Deluxe", accommodation.getRoomType());
    }
}
