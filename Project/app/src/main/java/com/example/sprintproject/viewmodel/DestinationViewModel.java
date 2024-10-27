package com.example.sprintproject.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sprintproject.model.Destination;
import com.example.sprintproject.model.DestinationList;
import com.google.firebase.firestore.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DestinationViewModel extends ViewModel {

    private DestinationList destinationList;
    private FirebaseFirestore db;


    public DestinationViewModel() {
        destinationList = DestinationList.getInstance();
        db = FirebaseFirestore.getInstance();


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
        Map<String, Object> destinationData = new HashMap<>();
        destinationData.put("destination", location);
        destinationData.put("start", startDate.toString());
        destinationData.put("end", endDate.toString());

        db.collection("Destination")
                .add(destinationData)
                .addOnSuccessListener(documentReference -> {
                    // Success
                    Log.d("Firestore", "DocumentSnapshot added with ID: " + documentReference.getId());
                })
                .addOnFailureListener(e -> {
                    // Failure
                    Log.w("Firestore", "Error adding document", e);
                });

        destinationList.addDestination(destination);
        return true;
    }
}
