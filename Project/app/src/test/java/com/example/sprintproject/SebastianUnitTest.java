package com.example.sprintproject;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.sprintproject.model.User;

import java.time.LocalDate;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class SebastianUnitTest {
    @Test
    public void testUserDurationCalculation() {
        LocalDate startDate = LocalDate.of(2004, 4, 10);
        LocalDate endDate = LocalDate.of(2004, 4, 28);
        User user = new User();

        user.setStartDate(startDate);
        user.setEndDate(endDate);

        user.calculateMissingValues();
        assertEquals(user.getDuration(), 18);
    }

    @Test
    public void testUserStartDateCalculation() {
        LocalDate startDate = LocalDate.of(2002, 12, 12);
        LocalDate endDate = LocalDate.of(2002, 12, 13);
        User user = new User();

        user.setDuration(1);
        user.setEndDate(endDate);

        user.calculateMissingValues();
        assertEquals(user.getStartDate(), startDate);
    }
}