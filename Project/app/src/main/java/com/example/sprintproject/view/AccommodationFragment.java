package com.example.sprintproject.view;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sprintproject.R;
import com.example.sprintproject.model.Accommodation;
import com.example.sprintproject.model.SortByCheckInStrategy;
import com.example.sprintproject.model.SortByCheckOutStrategy;
import com.example.sprintproject.model.SortByLocationStrategy;
import com.example.sprintproject.model.SortingStrategy;
import com.example.sprintproject.model.Trip;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class AccommodationFragment extends Fragment {

    private FirebaseFirestore db;  // Firestore instance
    private String activeTripId;
    private List<Accommodation> accommodations;
    private AccommodationAdapter adapter;

    private SortingStrategy sortingStrategy;  // Add sorting strategy
    private final SortByCheckInStrategy checkInStrategy = new SortByCheckInStrategy();
    private final SortByCheckOutStrategy checkOutStrategy = new SortByCheckOutStrategy();
    private final SortByLocationStrategy locationStrategy = new SortByLocationStrategy();

    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public AccommodationFragment() {}
  
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Initialize FirebaseFirestore instance
        db = FirebaseFirestore.getInstance();

        // Inflate the layout for the fragment
        return inflater.inflate(R.layout.fragment_accommodation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Ensure context is available
        if (getActivity() != null && getContext() != null) {
            // Set up UI components safely
            Button sortByCheckInButton = view.findViewById(R.id.sort_by_check_in_button);
            Button sortByCheckOutButton = view.findViewById(R.id.sort_by_check_out_button);
            Button sortByLocationButton = view.findViewById(R.id.sort_by_location_button);
            ImageButton addAccommodationButton = view.findViewById(R.id.addAccommodationButton);

            // Set listeners for sorting buttons
            sortByCheckInButton.setOnClickListener(v -> setSortingStrategy(checkInStrategy));
            sortByCheckOutButton.setOnClickListener(v -> setSortingStrategy(checkOutStrategy));
            sortByLocationButton.setOnClickListener(v -> setSortingStrategy(locationStrategy));

            // Set listener for the Add Accommodation button to show the dialog
            addAccommodationButton.setOnClickListener(v -> showAddAccommodationDialog());

            // Set up the adapter for the list view
            ListView accommodationListView = view.findViewById(R.id.accommodation_list);
            accommodations = new ArrayList<>();  // Initialize the accommodation list
            adapter = new AccommodationAdapter(getContext(),
                    accommodations);  // Pass context to adapter
            accommodationListView.setAdapter(adapter);
        } else {
            Log.e("AccommodationFragment",
                    "Activity or context is null. Unable to access UI components.");
        }

        loadActiveTrip();
    }

    private void setSortingStrategy(SortingStrategy strategy) {
        sortingStrategy = strategy;
        sortAccommodations();
    }

    private void sortAccommodations() {
        if (sortingStrategy != null && accommodations != null) {
            sortingStrategy.sort(accommodations);
            adapter.notifyDataSetChanged();
        }
    }

    private void loadActiveTrip() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Toast.makeText(getContext(), "User is not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = user.getUid();

        // Ensure Firestore instance is initialized
        if (db != null) {
            db.collection("User").document(userId).get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            activeTripId = documentSnapshot.getString("activeTrip");
                            if (activeTripId != null) {
                                loadTripData(activeTripId);
                            } else {
                                Toast.makeText(getContext(),
                                        "No active trip found for this user",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .addOnFailureListener(e -> Toast.makeText(getContext(),
                            "Error loading active trip",
                            Toast.LENGTH_SHORT).show());
        } else {
            Log.e("AccommodationFragment", "Firestore instance is null");
        }
    }

    private void loadTripData(String tripId) {
        db.collection("Trip").document(tripId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Trip trip = documentSnapshot.toObject(Trip.class);
                        if (trip != null) {
                            accommodations.clear();
                            accommodations.addAll(trip.getAccommodations());
                            sortAccommodations();  // Sort after loading data
                            adapter.notifyDataSetChanged();
                        }
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(getContext(),
                        "Error loading trip",
                        Toast.LENGTH_SHORT).show());
    }

    private void showAddAccommodationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_accommodation, null);
        builder.setView(dialogView);

        EditText checkInEdit = dialogView.findViewById(R.id.check_in);
        EditText checkOutEdit = dialogView.findViewById(R.id.check_out);
        EditText locationEdit = dialogView.findViewById(R.id.location);
        Spinner numOfRoomsSpinner = dialogView.findViewById(R.id.num_of_rooms);
        Spinner roomTypeSpinner = dialogView.findViewById(R.id.room_type);
        Button addAccommodationButton = dialogView.findViewById(R.id.button_add_accommodation);

        // Populate the number of rooms Spinner
        ArrayAdapter<CharSequence> numOfRoomsAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.number_of_rooms_options, android.R.layout.simple_spinner_item);
        numOfRoomsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        numOfRoomsSpinner.setAdapter(numOfRoomsAdapter);

        // Populate the room type Spinner
        ArrayAdapter<CharSequence> roomTypeAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.room_type_options, android.R.layout.simple_spinner_item);
        roomTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roomTypeSpinner.setAdapter(roomTypeAdapter);

        AlertDialog dialog = builder.create();

        addAccommodationButton.setOnClickListener(v -> {
            String location = locationEdit.getText().toString();
            String roomType = roomTypeSpinner.getSelectedItem().toString();
            int numOfRooms = Integer.parseInt(numOfRoomsSpinner.getSelectedItem().toString());
            String checkIn = checkInEdit.getText().toString();
            String checkOut = checkOutEdit.getText().toString();

            // Validate check-in and check-out date formats
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            try {
                LocalDateTime.parse(checkIn, formatter);
                LocalDateTime.parse(checkOut, formatter);
            } catch (Exception e) {
                Toast.makeText(getContext(), "Invalid datetime format. Please use yyyy-MM-dd HH:mm.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Check for duplicate accommodation
            isDuplicateAccommodation(location, checkIn, checkOut, isDuplicate -> {
                if (isDuplicate) {
                    Toast.makeText(getContext(), "Duplicate accommodation found! Not adding.", Toast.LENGTH_SHORT).show();
                    return; // Exit if duplicate is found
                } else {

                    // Only proceed to add if no duplicate
                    Accommodation newAccommodation = new Accommodation(location, "Hotel Name", checkIn, checkOut, numOfRooms, roomType);
                    db.collection("Trip").document(activeTripId)
                            .update("accommodations", FieldValue.arrayUnion(newAccommodation))
                            .addOnSuccessListener(aVoid -> {
                                accommodations.add(newAccommodation);
                                adapter.notifyDataSetChanged();
                                Toast.makeText(getContext(), "Accommodation added", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            })
                            .addOnFailureListener(e -> Toast.makeText(getContext(), "Error adding accommodation", Toast.LENGTH_SHORT).show());
                }
            });

        });

        dialog.show();
    }

    public void isDuplicateAccommodation(String location, String checkIn, String checkOut, Consumer<Boolean> callback) {
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

                        db.collection("Trip").document(tripId).get()
                                .addOnSuccessListener(tripDocument -> {
                                    if (tripDocument.exists()) {
                                        // Retrieve fields directly from Trip document
                                        String existingLocation = tripDocument.getString("Location");
                                        String existingCheckIn = tripDocument.getString("CheckIn");
                                        String existingCheckOut = tripDocument.getString("CheckOut");

                                        // Log values for debugging
                                        Log.d("DuplicateCheck", "Checking Trip: " +
                                                "Location = " + existingLocation +
                                                ", CheckIn = " + existingCheckIn +
                                                ", CheckOut = " + existingCheckOut);
                                        Log.d("DuplicateCheck", "Against: " +
                                                "Location = " + location +
                                                ", CheckIn = " + checkIn +
                                                ", CheckOut = " + checkOut);

                                        // Check if location, check-in, and check-out match
                                        if (existingLocation != null && existingLocation.equalsIgnoreCase(location) &&
                                                existingCheckIn != null && existingCheckIn.equals(checkIn) &&
                                                existingCheckOut != null && existingCheckOut.equals(checkOut)) {
                                            callback.accept(true); // Duplicate found
                                        } else {
                                            callback.accept(false); // No duplicate found
                                        }
                                    } else {
                                        Log.w("Firestore", "Trip document not found.");
                                        callback.accept(false); // No trip document found, so no duplicates
                                    }
                                })
                                .addOnFailureListener(e -> {
                                    Log.w("Firestore", "Error retrieving trip document", e);
                                    callback.accept(false); // Treat failure as no duplicate
                                });
                    } else {
                        Log.w("Firestore", "No active trip found for user.");
                        callback.accept(false); // No active trip, so no duplicates
                    }
                })
                .addOnFailureListener(e -> {
                    Log.w("Firestore", "Error retrieving user document", e);
                    callback.accept(false); // Treat failure as no duplicate
                });
    }


}
