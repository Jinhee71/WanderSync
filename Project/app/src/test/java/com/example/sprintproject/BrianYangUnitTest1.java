package com.example.sprintproject;

import static org.junit.Assert.assertEquals;

import com.example.sprintproject.model.User;

import org.junit.Test;

import java.time.LocalDate;

public class BrianYangUnitTest1 {

    @Test
    public void testUserDurationCalculation() {
        LocalDate startDate = LocalDate.of(2007, 3, 12);
        LocalDate endDate = LocalDate.of(2007, 3, 31);
        User user = new User();

        user.setStartDate(startDate);
        user.setEndDate(endDate);

        user.calculateMissingValues();
        assertEquals(user.getDuration(), 19);
    }

    @Test
    public void testUserStartDateCalculation() {
        LocalDate startDate = LocalDate.of(2017, 2, 12);
        LocalDate endDate = LocalDate.of(2017, 2, 20);
        User user = new User();

        user.setDuration(8);
        user.setEndDate(endDate);

        user.calculateMissingValues();
        assertEquals(user.getStartDate(), startDate);
    }

    @Test
    public void testUserEndDateCalculation() {
        LocalDate startDate = LocalDate.of(2005, 11, 13);
        LocalDate endDate = LocalDate.of(2005, 11, 21);
        User user = new User();

        user.setStartDate(startDate);
        user.setDuration(8);

        user.calculateMissingValues();
        assertEquals(user.getEndDate(), endDate);
    }
}
