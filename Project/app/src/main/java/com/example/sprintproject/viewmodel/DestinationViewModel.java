package com.example.sprintproject.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sprintproject.model.Destination;
import com.example.sprintproject.model.DestinationList;

import java.time.LocalDate;
import java.util.List;

public class DestinationViewModel extends ViewModel {
    private DestinationList destinationList;

    public DestinationViewModel() {
        destinationList = DestinationList.getInstance();
    }

    public LiveData<List<Destination>> getDestinations() {
        MutableLiveData<List<Destination>> liveData = new MutableLiveData<>();
        liveData.setValue(destinationList.getDestinations());
        return liveData;
    }

    public boolean addDestination(String location, LocalDate startDate, LocalDate endDate) {
        if (location == null || location.trim().isEmpty() || startDate == null || endDate == null || startDate.isAfter(endDate)) {
            return false;  // Return false if input validation fails
        }
        Destination destination = new Destination(location, startDate, endDate);
        destinationList.addDestination(destination);
        return true;
    }
}
