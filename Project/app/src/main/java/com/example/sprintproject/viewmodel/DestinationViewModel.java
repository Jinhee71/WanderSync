package com.example.sprintproject.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sprintproject.model.Destination;
import com.example.sprintproject.model.DestinationList;
import com.google.firebase.firestore.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DestinationViewModel extends ViewModel {

    private DestinationList destinationList;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    public DestinationViewModel() {
        destinationList = DestinationList.getInstance();
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

    }

    public LiveData<List<Destination>> getDestinations() {
        MutableLiveData<List<Destination>> liveData = new MutableLiveData<>();
        liveData.setValue(destinationList.getDestinations());
        return liveData;
    }

    public boolean addDestination(String location, LocalDate startDate, LocalDate endDate) {
        if (location == null || location.trim().isEmpty() || startDate == null || endDate == null || startDate.isAfter(endDate)) {
            return false;
        }
        Destination destination = new Destination(location, startDate, endDate);
        destinationList.addDestination(destination);

        String userId = mAuth.getCurrentUser().getUid();
        db.collection("User").document(userId).get()
                .addOnSuccessListener(userDocument -> {
                    if (userDocument.exists() && userDocument.contains("activeTrip")) {
                        String tripId = userDocument.getString("activeTrip");
                        addDestinationToTrip(tripId, location, startDate, endDate); //Add Destination to Trip ID
                    } else {
                        Log.w("Firestore", "No active trip found for user.");
                    }
                })
                .addOnFailureListener(e -> Log.w("Firestore", "Error retrieving user document", e));

        return true;
    }

    private void addDestinationToTrip(String tripId, String location, LocalDate startDate, LocalDate endDate) {
        Map<String, Object> destinationData = new HashMap<>();
        destinationData.put("destination name", location);
        destinationData.put("start date", startDate.toString());
        destinationData.put("end date", endDate.toString());

        db.collection("Trip").document(tripId).collection("Destination")
                .add(destinationData)
                .addOnSuccessListener(documentReference -> {
                    Log.d("Firestore", "Destination added with ID: " + documentReference.getId());
                })
                .addOnFailureListener(e -> Log.w("Firestore", "Error adding destination to trip", e));
    }

}
