package com.example.sprintproject;
import static org.junit.Assert.*;

import com.example.sprintproject.model.Accommodation;
import com.example.sprintproject.model.Destination;
import com.example.sprintproject.model.Dining;
import com.example.sprintproject.model.TravelCommunity;
import static com.example.sprintproject.model.TravelCommunityFactory.createPost;

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

    @Test // Test setting and getting the location
    public void testDiningSetLocation() {
        Dining dining = new Dining();
        dining.setLocation("New Orleans");

        assertEquals("New Orleans", dining.getLocation());
    }


    @Test // Test setting and getting the location
    public void testSetLocation() {
        Accommodation accommodation = new Accommodation();
        accommodation.setLocation("Seoul");

        assertEquals("Seoul", accommodation.getLocation());
    }

    @Test
    public void testCreatePost() {
        String destination = "Korea";
        long duration =7;

        TravelCommunity newPost = createPost(destination,duration);

        assertEquals("Destination should match the input",destination, newPost.getDestination());
        assertEquals("Duration should match the input", duration, newPost.getDuration());
    }
    @Test
    public void testTravelCommunityBasicConstructor(){
        long duration = 7;
        String destination = "Korea";
        String accommodations = "Hotel365";
        String diningReservations = "Sulbing";
        String notes = "coffee and ice cream were delicious";
        String username = "hkim3169";

        TravelCommunity travelCommunity = new TravelCommunity(duration, destination, accommodations,
                diningReservations, notes, username);

        assertEquals("Duration should match the input", duration, travelCommunity.getDuration());
        assertEquals("Destination should match the input", destination, travelCommunity.getDestination());
        assertEquals("Accommodations should match the input", accommodations, travelCommunity.getAccommodations());
    }

}