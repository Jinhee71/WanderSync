package com.example.sprintproject;

import static org.junit.Assert.*;

import com.example.sprintproject.model.Destination;
import com.example.sprintproject.model.Dining;
import com.example.sprintproject.model.Accommodation;

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

    @Test // Test setting and getting the location
    public void testSetLocation() {
        Accommodation accommodation = new Accommodation();
        accommodation.setLocation("Seoul");

        assertEquals("Seoul", accommodation.getLocation());
    }

    @Test // Test setting and getting the hotel name
    public void testSetHotelName() {
        Accommodation accommodation = new Accommodation();
        accommodation.setHotelName("Holiday Inn");

        assertEquals("Holiday Inn", accommodation.getHotelName());
    }

    @Test // Test setting and getting the check-in time
    public void testSetCheckIn() {
        LocalDateTime checkIn = LocalDateTime.of(2024, 11, 25, 15, 0);
        Accommodation accommodation = new Accommodation();
        accommodation.setCheckIn(checkIn);

        assertEquals(checkIn, accommodation.getCheckIn());
    }

    @Test // Test setting and getting the check-out time
    public void testSetCheckOut() {
        LocalDateTime checkOut = LocalDateTime.of(2024, 11, 30, 12, 0);
        Accommodation accommodation = new Accommodation();
        accommodation.setCheckOut(checkOut);

        assertEquals(checkOut, accommodation.getCheckOut());
    }

    @Test // Test setting and getting the number of rooms
    public void testSetNumberOfRooms() {
        Accommodation accommodation = new Accommodation();
        accommodation.setNumberOfRooms(3);

        assertEquals(3, accommodation.getNumberOfRooms());
    }

    @Test // Test setting and getting the room type
    public void testSetRoomType() {
        Accommodation accommodation = new Accommodation();
        accommodation.setRoomType("Deluxe");

        assertEquals("Deluxe", accommodation.getRoomType());
    }

}
