package com.example.sprintproject.viewmodel;

import android.util.Log;
import androidx.lifecycle.ViewModel;
import com.example.sprintproject.model.TravelCommunity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TravelCommunityViewModel extends ViewModel {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public interface FetchCallback {
        void onFetchComplete(List<TravelCommunity> travelCommunities);
    }

    public void fetchTravelCommunities(FetchCallback callback) {
        String userId = mAuth.getCurrentUser() != null ? mAuth.getCurrentUser().getUid() : null;
        if (userId == null) {
            Log.w("Auth", "User not authenticated.");
            callback.onFetchComplete(new ArrayList<>()); // Return empty list if not authenticated
            return;
        }

        db.collection("User").document(userId).get()
                .addOnSuccessListener(userDocument -> {
                    if (userDocument.exists() && userDocument.contains("activeTrip")) {
                        String tripId = userDocument.getString("activeTrip");

                        db.collection("Trip").document(tripId).collection("TravelCommunity")
                                .get()
                                .addOnSuccessListener(querySnapshot -> {
                                    List<TravelCommunity> travelCommunities = new ArrayList<>();
                                    for (QueryDocumentSnapshot document : querySnapshot) {
                                        try {
                                            String notes = document.getString("Notes");
                                            String destination = document.getString("Destination");
                                            String accommodation = document.getString("Accommodation");
                                            String dining = document.getString("Dining");

                                            long duration = document.getLong("Duration");
                                            travelCommunities.add(new TravelCommunity(duration, destination, accommodation, dining, notes));
                                        } catch (Exception e) {
                                            Log.w("TravelCommunityViewModel", "Error parsing document: " + document.getId(), e);
                                        }
                                    }
                                    callback.onFetchComplete(travelCommunities);
                                })
                                .addOnFailureListener(e -> {
                                    Log.w("Firestore", "Error loading travel communities", e);
                                    callback.onFetchComplete(new ArrayList<>()); // Return empty list on error
                                });
                    } else {
                        Log.w("Firestore", "No active trip found for user.");
                        callback.onFetchComplete(new ArrayList<>()); // Return empty list if no trip found
                    }
                })
                .addOnFailureListener(e -> {
                    Log.w("Firestore", "Error retrieving user document", e);
                    callback.onFetchComplete(new ArrayList<>()); // Return empty list on error
                });
    }

    public void addTravelCommunity(long duration, String destination,
                                   String accommodation, String dining,
                                   String notes) {
        String userId = mAuth.getCurrentUser() != null ? mAuth.getCurrentUser().getUid() : null;
        if (userId == null) {
            Log.w("Auth", "User not authenticated.");
            return;
        }

        db.collection("User").document(userId).get()
                .addOnSuccessListener(userDocument -> {
                    if (userDocument.exists() && userDocument.contains("activeTrip")) {
                        String tripId = userDocument.getString("activeTrip");
                        addTravelCommunityToTrip(duration, destination, accommodation, dining, notes, tripId);
                    } else {
                        Log.w("Firestore", "No active trip found for user.");
                    }
                })
                .addOnFailureListener(e -> Log.w("Firestore", "Error retrieving user document", e));
    }

    private void addTravelCommunityToTrip(long duration, String destination,
                                          String accommodation, String dining,
                                          String notes, String tripId) {
        HashMap<String, Object> travelCommunityData = new HashMap<>();
        travelCommunityData.put("Duration", duration);
        travelCommunityData.put("Destination", destination);
        travelCommunityData.put("Accommodation", accommodation);
        travelCommunityData.put("Dining", dining);
        travelCommunityData.put("Notes", notes);

        db.collection("Trip").document(tripId).collection("TravelCommunity")
                .add(travelCommunityData)
                .addOnSuccessListener(documentReference -> {
                    Log.d("Firestore", "Travel Community added with ID: " + documentReference.getId());
                })
                .addOnFailureListener(e -> Log.w("Firestore", "Error adding travel community", e));
    }
}
