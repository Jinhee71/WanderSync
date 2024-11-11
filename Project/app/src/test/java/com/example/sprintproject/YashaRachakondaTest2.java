package com.example.sprintproject;

import static org.junit.Assert.assertEquals;

import com.example.sprintproject.model.Accommodation;
import com.example.sprintproject.model.Destination;

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

    @Test // Test Accommodation object creation with parameters
    public void testAccommodationObjectCreation() {
        LocalDateTime checkIn = LocalDateTime.of(2024, 12, 1, 14, 0);
        LocalDateTime checkOut = LocalDateTime.of(2024, 12, 10, 11, 0);
        Accommodation accommodation = new Accommodation("Paris", "Hotel Luxe", checkIn, checkOut, 2, "Suite");

        assertEquals("Paris", accommodation.getLocation());
        assertEquals("Hotel Luxe", accommodation.getHotelName());
        assertEquals(checkIn, accommodation.getCheckIn());
        assertEquals(checkOut, accommodation.getCheckOut());
        assertEquals(2, accommodation.getNumberOfRooms());
        assertEquals("Suite", accommodation.getRoomType());
    }

}


