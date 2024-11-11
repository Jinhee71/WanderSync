package com.example.sprintproject.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortByCheckOutStrategy implements SortingStrategy {
    @Override
    public void sort(List<Accommodation> accommodations) {
        Collections.sort(accommodations, new Comparator<Accommodation>() {
            @Override
            public int compare(Accommodation a1, Accommodation a2) {
                return a1.getCheckOut().compareTo(a2.getCheckOut());
            }
        });
    }
}
