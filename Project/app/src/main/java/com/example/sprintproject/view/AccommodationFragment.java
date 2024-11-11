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

import java.util.ArrayList;
import java.util.List;

public class AccommodationFragment extends Fragment {

    private FirebaseFirestore db;  // Firestore instance
    private String activeTripId;
    private List<Accommodation> accommodations;
    private AccommodationAdapter adapter;

    private SortingStrategy sortingStrategy;  // Add sorting strategy
    private final SortByCheckInStrategy checkInStrategy = new SortByCheckInStrategy();
    private final SortByCheckOutStrategy checkOutStrategy = new SortByCheckOutStrategy();
    private final SortByLocationStrategy locationStrategy = new SortByLocationStrategy();

    public AccommodationFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
            adapter = new AccommodationAdapter(getContext(), accommodations);  // Pass context to adapter
            accommodationListView.setAdapter(adapter);
        } else {
            Log.e("AccommodationFragment", "Activity or context is null. Unable to access UI components.");
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
                                Toast.makeText(getContext(), "No active trip found for this user", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .addOnFailureListener(e -> Toast.makeText(getContext(), "Error loading active trip", Toast.LENGTH_SHORT).show());
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
                .addOnFailureListener(e -> Toast.makeText(getContext(), "Error loading trip", Toast.LENGTH_SHORT).show());
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

            // Get check-in and check-out dates as strings
            String checkIn = checkInEdit.getText().toString();
            String checkOut = checkOutEdit.getText().toString();

            // Validate that the dates are in correct format (e.g. "yyyy-MM-dd HH:mm")
            if (checkIn.isEmpty() || checkOut.isEmpty()) {
                Toast.makeText(getContext(), "Please enter valid check-in and check-out dates", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create a new accommodation object
            Accommodation newAccommodation = new Accommodation(location, "Hotel Name", checkIn, checkOut, numOfRooms, roomType);

            // Add the new accommodation to the trip's accommodations list in Firestore
            db.collection("Trip").document(activeTripId)
                    .update("accommodations", FieldValue.arrayUnion(newAccommodation))
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(getContext(), "Accommodation added", Toast.LENGTH_SHORT).show();
                        accommodations.add(newAccommodation);
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    })
                    .addOnFailureListener(e -> Toast.makeText(getContext(), "Error adding accommodation", Toast.LENGTH_SHORT).show());
        });

        dialog.show();
    }
}
