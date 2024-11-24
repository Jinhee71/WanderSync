package com.example.sprintproject;

import static org.junit.Assert.*;

import com.example.sprintproject.model.TravelCommunity;

import org.junit.Test;

public class JinheeLeeUnitTest2 {
    @Test
    public void testTravelCommunitySetDestination() {
        String destination = "New York";
        TravelCommunity travelCommunity = new TravelCommunity();
        travelCommunity.setDestination(destination);

        assertEquals("New York", travelCommunity.getDestination());
    }

    @Test
    public void testTravelCommunitySetDuration() {
        TravelCommunity travelCommunity = new TravelCommunity();
        travelCommunity.setDuration(6);

        assertEquals(6, travelCommunity.getDuration());
    }
}
