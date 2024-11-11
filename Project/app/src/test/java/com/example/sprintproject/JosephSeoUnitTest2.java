package com.example.sprintproject;

import static org.junit.Assert.assertEquals;

import com.example.sprintproject.model.Accommodation;
import com.example.sprintproject.model.Dining;

import org.junit.Test;

public class JosephSeoUnitTest2 {

    @Test // Test setting and getting the website
    public void testDiningSetWebsite() {
        Dining dining = new Dining();
        dining.setWebsite("https://2340.com");

        assertEquals("https://2340.com", dining.getWebsite());
    }

    @Test // Test setting and getting the hotel name
    public void testSetHotelName() {
        Accommodation accommodation = new Accommodation();
        accommodation.setHotelName("Holiday Inn");

        assertEquals("Holiday Inn", accommodation.getHotelName());
    }
}
