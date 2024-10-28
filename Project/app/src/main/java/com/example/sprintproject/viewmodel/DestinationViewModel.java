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
import java.time.temporal.ChronoUnit;
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
                        updateTripDuration(tripId);
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

    private void updateTripDuration(String tripId) {
        db.collection("Trip").document(tripId).collection("Destination")
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    long tripDuration = 0;
                    if (!querySnapshot.isEmpty()) {
                        LocalDate earliestStartDate = null;
                        LocalDate latestEndDate = null;

<<<<<<< HEAD

=======
                        for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                            LocalDate startDate = LocalDate.parse(document.getString("start date"));
                            LocalDate endDate = LocalDate.parse(document.getString("end date"));

                            tripDuration += ChronoUnit.DAYS.between(startDate, endDate);
                        }

                        // Update tripDuration in Firestore after calculating
                        updateTripDurationInFirestore(tripId, tripDuration);
                    }
                })
                .addOnFailureListener(e -> Log.w("Firestore", "Error retrieving destinations for trip duration update", e));
    }

    private void updateTripDurationInFirestore(String tripId, long tripDuration) {
        db.collection("Trip").document(tripId)
                .update("duration", tripDuration)
                .addOnSuccessListener(aVoid -> Log.d("Firestore", "Trip duration updated to " + tripDuration + " days"))
                .addOnFailureListener(e -> Log.w("Firestore", "Error updating trip duration", e));
    }

    public boolean updateAllocated(long duration, LocalDate startDate, LocalDate endDate){
        String userId = mAuth.getCurrentUser().getUid();
        db.collection("User").document(userId).update("allocatedDays",duration)
                .addOnSuccessListener(documentReference -> {
                    Log.d("Firestore", "duration updated with " + duration);
                })
                .addOnFailureListener(e -> Log.w("Firestore", "Error adding destination to trip", e));
        return true;
    }
>>>>>>> 144c0afc6855f70879cfc09f4a3e6de7320d9f4d
}
