package com.example.sprintproject;



import static org.junit.Assert.*;



import com.example.sprintproject.model.Destination;
import com.example.sprintproject.model.Trip;
import com.example.sprintproject.model.User;


import org.junit.Before;
import org.junit.Test;


import java.time.LocalDate;


public class JosephSeoUnitTest {
    private Trip trip;
    private Destination destination;
    private User user;

    @Before
    public void setUp() {
        trip = new Trip();
        destination = new Destination("Paris", LocalDate.now(), LocalDate.now().plusDays(5)); // Create a Destination
        user = new User();
    }

    @Test
    public void testAddDestination() {
        trip.addDestination(destination);

        assertEquals(1, trip.getDestinations().size());
        assertEquals(destination, trip.getDestinations().get(0));
    }

    @Test
    public void testAddInvitedUser() {
        trip.addInvitedUser(user);

        assertEquals(1, trip.getInvitedUsers().size());
        assertEquals(user, trip.getInvitedUsers().get(0));
    }
}
