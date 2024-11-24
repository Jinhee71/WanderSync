package com.example.sprintproject.viewmodel;

import android.util.Log;
import androidx.lifecycle.ViewModel;
import com.example.sprintproject.model.Dining;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class DiningViewModel extends ViewModel {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public interface FetchCallback {
        void onFetchComplete(List<Dining> diningReservations);
    }

    public void fetchDiningReservations(FetchCallback callback) {
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

                        db.collection("Trip").document(tripId).collection("Dining")
                                .get()
                                .addOnSuccessListener(querySnapshot -> {
                                    List<Dining> reservations = new ArrayList<>();
                                    for (QueryDocumentSnapshot document : querySnapshot) {
                                        try {
                                            String location = document.getString("Location");
                                            String website = document.getString("Website");
                                            String reservationTimeStr
                                                    = document.getString("Reservation Time");
                                            LocalDateTime reservationTime
                                                    = reservationTimeStr != null
                                                    ? LocalDateTime.parse(reservationTimeStr)
                                                    : LocalDateTime.now();
                                            long review = document.getLong("Review") != null
                                                    ? document.getLong("Review") : 0;

                                            reservations.add(new Dining(
                                                    website, location, reservationTime, review));
                                        } catch (DateTimeParseException e) {
                                            Log.w("DiningViewModel",
                                                    "Invalid date format in document: "
                                                            + document.getId(), e);
                                        }
                                    }
                                    callback.onFetchComplete(reservations);
                                })
                                .addOnFailureListener(e -> {
                                    Log.w("Firestore", "Error loading dining reservations", e);
                                    callback.onFetchComplete(new ArrayList<>());
                                });
                    } else {
                        Log.w("Firestore", "No active trip found for user.");
                        callback.onFetchComplete(new ArrayList<>());
                    }
                })
                .addOnFailureListener(e -> {
                    Log.w("Firestore", "Error retrieving user document", e);
                    callback.onFetchComplete(new ArrayList<>()); // Return empty list on error
                });
    }

    public void addDining(String website,
                          String location, LocalDateTime reservationTime, long review) {
        String userId = mAuth.getCurrentUser() != null ? mAuth.getCurrentUser().getUid() : null;
        if (userId == null) {
            Log.w("Auth", "User not authenticated.");
            return;
        }

        db.collection("User").document(userId).get()
                .addOnSuccessListener(userDocument -> {
                    if (userDocument.exists() && userDocument.contains("activeTrip")) {
                        String tripId = userDocument.getString("activeTrip");
                        addDiningToTrip(website, location, reservationTime, review, tripId);
                    } else {
                        Log.w("Firestore", "No active trip found for user.");
                    }
                })
                .addOnFailureListener(e -> Log.w("Firestore", "Error retrieving user document", e));
    }

    private void addDiningToTrip(String website, String location,
                                 LocalDateTime reservationTime, long review, String tripId) {
        HashMap<String, Object> diningData = new HashMap<String, Object>();
        diningData.put("Website", website);
        diningData.put("Location", location);
        diningData.put("Reservation Time", reservationTime.toString());
        diningData.put("Review", review);

        db.collection("Trip").document(tripId).collection("Dining")
                .add(diningData)
                .addOnSuccessListener(documentReference -> {
                    Log.d("Firestore", "Dining added with ID: " + documentReference.getId());
                })
                .addOnFailureListener(e -> Log.w("Firestore",
                        "Error adding dining reservation", e));
    }

    public void isDuplicateReservation(String location,
                                       LocalDateTime reservationTime, Consumer<Boolean> callback) {
        String userId = mAuth.getCurrentUser() != null ? mAuth.getCurrentUser().getUid() : null;
        if (userId == null) {
            Log.w("Auth", "User not authenticated.");
            callback.accept(false);
            return;
        }

        db.collection("User").document(userId).get()
                .addOnSuccessListener(userDocument -> {
                    if (userDocument.exists() && userDocument.contains("activeTrip")) {
                        String tripId = userDocument.getString("activeTrip");

                        db.collection("Trip").document(tripId).collection("Dining")
                                .get()
                                .addOnSuccessListener(querySnapshot -> {
                                    for (QueryDocumentSnapshot document : querySnapshot) {
                                        String existingLocation = document.getString("Location");
                                        String reservationTimeStr =
                                                document.getString("Reservation Time");
                                        LocalDateTime existingTime = reservationTimeStr != null
                                                ? LocalDateTime.parse(reservationTimeStr)
                                                : LocalDateTime.now();

                                        // Check if both location and reservation time match
                                        if (existingLocation.equalsIgnoreCase(location)
                                                && existingTime.equals(reservationTime)) {
                                            callback.accept(true); // Duplicate found
                                            return;
                                        }
                                    }
                                    callback.accept(false);
                                })
                                .addOnFailureListener(e -> {
                                    Log.w("Firestore",
                                            "Error checking duplicate dining reservations", e);
                                    callback.accept(false);
                                });
                    } else {
                        Log.w("Firestore", "No active trip found for user.");
                        callback.accept(false);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.w("Firestore", "Error retrieving user document", e);
                    callback.accept(false); // Treat failure as no duplicate
                });
    }


}
