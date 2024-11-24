package com.example.sprintproject.model;

public class TravelCommunityFactory {

    // Factory method to create TravelCommunity posts
    public static TravelCommunity createPost(String destination, long duration) {

        TravelCommunity post = new TravelCommunity();

        post.setDestination(destination);

        post.setDuration(duration);

        post.setTimestamp(System.currentTimeMillis());

        return post;
    }
}
