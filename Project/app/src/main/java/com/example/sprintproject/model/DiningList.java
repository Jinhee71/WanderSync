package com.example.sprintproject.model;

import java.util.ArrayList;
import java.util.List;

public class DiningList {
    private static DiningList instance;
    private List<Dining> diningList;

    // Private constructor to restrict instantiation
    private DiningList() {
        diningList = new ArrayList<>();

    }

    // Get the single instance of DestinationList
    public static DiningList getInstance() {
        if (instance == null) {
            instance = new DiningList();
        }
        return instance;
    }

    // Add a destination to the list
    public void addDining(Dining dining) {
        diningList.add(dining);
    }

    // Get the list of all destinations
    public List<Dining> getDiningList() {
        return diningList;
    }

}
