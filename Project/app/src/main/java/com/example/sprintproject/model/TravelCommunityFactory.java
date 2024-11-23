package com.example.sprintproject.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

public class TravelCommunityFactory {

    // Factory method to create TravelCommunity posts
    public static TravelCommunity createPost(String destination, String duration) {
        // Convert the destination to a List<String>
        TravelCommunity post = new TravelCommunity();

        // Wrap destination into a List<String>
        post.setDestination(Collections.singletonList(destination));

        post.setDuration(duration);

        // Set the timestamp using current time
        post.setTimestamp(System.currentTimeMillis());

        return post;
    }
}
