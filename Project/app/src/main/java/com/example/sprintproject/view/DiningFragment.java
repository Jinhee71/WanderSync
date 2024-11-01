package com.example.sprintproject.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sprintproject.R;


import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DiningFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dining, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
            String time = editTime.getText().toString();
            String location = editLocation.getText().toString();
            String website = editWebsite.getText().toString();

            // Handle the input data as needed (e.g., save to database, update UI)
            // For now, we just close the dialog
            dialog.dismiss();
        });

        dialog.show();
    }
}