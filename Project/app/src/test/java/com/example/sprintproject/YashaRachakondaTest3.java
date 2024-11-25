package com.example.sprintproject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.example.sprintproject.model.TravelCommunity;

import org.junit.Test;


public class YashaRachakondaTest3 {
    @Test
    public void testConstructorAndGetters() {
        // Arrange
        long expectedDuration = 7;
        String expectedDestination = "Paris";
        String expectedAccommodations = "Hotel Eiffel";
        String expectedDiningReservations = "Le Jules Verne";
        String expectedNotes = "Visit museums and Eiffel Tower";
        String expectedUsername = "testuser@example.com";

        TravelCommunity travelCommunity = new TravelCommunity(
                expectedDuration, expectedDestination, expectedAccommodations,
                expectedDiningReservations, expectedNotes, expectedUsername
        );

        assertEquals(expectedDuration, travelCommunity.getDuration());
        assertEquals(expectedDestination, travelCommunity.getDestination());
        assertEquals(expectedAccommodations, travelCommunity.getAccommodations());
        assertEquals(expectedDiningReservations, travelCommunity.getDiningReservations());
        assertEquals(expectedNotes, travelCommunity.getNotes());
        assertEquals(expectedUsername, travelCommunity.getUsername());
        assertTrue(travelCommunity.getTimestamp() > 0); // Ensure timestamp is set
    }

    @Test
    public void testSetters() {
        TravelCommunity travelCommunity = new TravelCommunity();

        long newDuration = 5;
        String newDestination = "New York";
        String newAccommodations = "Marriott Hotel";
        String newDiningReservations = "Carmine's";
        String newNotes = "See Broadway shows";
        String newUsername = "newuser@example.com";
        long newTimestamp = System.currentTimeMillis();

        travelCommunity.setDuration(newDuration);
        travelCommunity.setDestination(newDestination);
        travelCommunity.setAccommodations(newAccommodations);
        travelCommunity.setDiningReservations(newDiningReservations);
        travelCommunity.setNotes(newNotes);
        travelCommunity.setUsername(newUsername);
        travelCommunity.setTimestamp(newTimestamp);

        assertEquals(newDuration, travelCommunity.getDuration());
        assertEquals(newDestination, travelCommunity.getDestination());
        assertEquals(newAccommodations, travelCommunity.getAccommodations());
        assertEquals(newDiningReservations, travelCommunity.getDiningReservations());
        assertEquals(newNotes, travelCommunity.getNotes());
        assertEquals(newUsername, travelCommunity.getUsername());
        assertEquals(newTimestamp, travelCommunity.getTimestamp());
    }
}


