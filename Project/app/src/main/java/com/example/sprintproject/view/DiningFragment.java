package com.example.sprintproject.view;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sprintproject.R;
import com.example.sprintproject.model.Dining;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DiningFragment extends Fragment {

    public DiningFragment() { }

    private List<Dining> diningReservations;
    private DiningReservationAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dining, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize the dining reservations list
        diningReservations = new ArrayList<>();

        // Initialize the adapter with the context and diningReservations list
        adapter = new DiningReservationAdapter(getContext(), diningReservations);

        // Set up the ListView and attach the adapter
        ListView listView = view.findViewById(R.id.reservation_list);
        listView.setAdapter(adapter);

        // Find the ImageButton by its ID and set an OnClickListener
        ImageButton addDiningButton = view.findViewById(R.id.addDiningButton);
        addDiningButton.setOnClickListener(v -> showAddReservationDialog());
    }

    // Method to show the dialog for adding a new reservation
    private void showAddReservationDialog() {
        // Create a new AlertDialog.Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        // Inflate the custom layout for the dialog
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_reservation, null);
        builder.setView(dialogView);

        // Find the EditText fields and the Add button inside the dialog
        EditText editTime = dialogView.findViewById(R.id.edit_time);
        EditText editLocation = dialogView.findViewById(R.id.edit_location);
        EditText editWebsite = dialogView.findViewById(R.id.edit_website);
        Button addReservationButton = dialogView.findViewById(R.id.button_add_reservation);

        // Create and show the dialog
        AlertDialog dialog = builder.create();

        // Set an OnClickListener on the "Add Reservation" button
        addReservationButton.setOnClickListener(v -> {
            // Retrieve input data from the EditText fields
            String timeInput = editTime.getText().toString();
            String location = editLocation.getText().toString();
            String website = editWebsite.getText().toString();

            // Parse time input to LocalDateTime (for simplicity, using current time if parsing fails)
            LocalDateTime reservationTime;
            try {
                reservationTime = LocalDateTime.parse(timeInput);
            } catch (Exception e) {
                reservationTime = LocalDateTime.now();
            }

            // Add new Dining reservation to the list and update the adapter
            diningReservations.add(new Dining(location, website, reservationTime, 5)); // Assuming 5 as a placeholder for review
            adapter.notifyDataSetChanged();

            // Close the dialog
            dialog.dismiss();
        });

        dialog.show();
    }
}
