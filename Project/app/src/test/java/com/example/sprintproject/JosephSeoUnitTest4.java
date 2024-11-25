package com.example.sprintproject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.example.sprintproject.model.Accommodation;
import com.example.sprintproject.model.Dining;
import com.example.sprintproject.model.TravelCommunity;
import com.example.sprintproject.model.TravelCommunityFactory;

import org.junit.Test;

public class JosephSeoUnitTest4 {

    @Test
    public void testCreatePost() {
        // Arrange
        String destination = "Test Destination";
        long duration = 7;

        // Act
        TravelCommunity post = TravelCommunityFactory.createPost(destination, duration);

        // Assert
        assertNotNull("TravelCommunity should not be null", post);
        assertEquals("Destination should be set correctly", destination, post.getDestination());
        assertEquals("Duration should be set correctly", duration, post.getDuration());
    }
    @Test
    public void testCreatePostTimestamp() {
        // Arrange
        String destination = "Test Destination";
        long duration = 7;

        // Act
        TravelCommunity post = TravelCommunityFactory.createPost(destination, duration);

        // Assert
        long timestamp = post.getTimestamp();
        long currentTime = System.currentTimeMillis();

        // Assert that the timestamp is close to the current time
        assertNotNull("Timestamp should not be null", timestamp);
        assertTrue("Timestamp should be a valid positive value", timestamp > 0);
        assertTrue("Timestamp should be close to the current time", Math.abs(currentTime - timestamp) < 1000); // Allow 1-second margin
    }
}
