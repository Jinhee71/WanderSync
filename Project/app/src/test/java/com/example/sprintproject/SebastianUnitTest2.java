package com.example.sprintproject;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.sprintproject.model.Accommodation;
import com.example.sprintproject.model.User;

import java.time.LocalDate;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class SebastianUnitTest2 {
    @Test // Test setting and getting the check-out time
    public void testSetCheckOut() {
        String checkOut = "2024-11-30 12:00";
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
}