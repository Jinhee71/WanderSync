package com.example.sprintproject;

import static com.example.sprintproject.model.TravelCommunityFactory.createPost;

import org.junit.Test;

import com.example.sprintproject.model.TravelCommunity;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class SebastianUnitTest3 {
    @Test
    public void testVanillaTravelCommunityCreation() {
        long duration = 5;
        String destination = "PHX";
        String accommodations = "Accommodation 1";
        String diningReservations = "Dining Res 1";
        String notes = "Note 1\nNote 2";
        String username = "test username!";

        TravelCommunity testCommunity = new TravelCommunity(duration, destination, accommodations,
                diningReservations, notes, username);

        assert(testCommunity.getDuration() == duration);
        assert(testCommunity.getDestination().equals(destination));
        assert(testCommunity.getAccommodations().equals(accommodations));
        assert(testCommunity.getDiningReservations().equals(diningReservations));
        assert(testCommunity.getNotes().equals(notes));
        assert(testCommunity.getUsername().equals(username));
    }

    @Test // test post creation with factory
    public void testPostCreation() {
        String destination = "PHX";
        long duration = 5;

        TravelCommunity post = createPost(destination, duration);

        assert (post.getDuration() == duration);
        assert (post.getDestination().equals(destination));
    }
}