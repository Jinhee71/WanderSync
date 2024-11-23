package com.example.sprintproject.view;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.sprintproject.R;
import com.example.sprintproject.model.TravelCommunity;
import com.example.sprintproject.model.TravelCommunityFactory;
import com.example.sprintproject.viewmodel.CommunityViewModel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class CommunityFragment extends Fragment {

    private List<TravelCommunity> travelPosts;
    private CommunityAdapter adapter;
    private CommunityViewModel communityViewModel;


    public CommunityFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_community, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize the Travel Post List
        travelPosts = new ArrayList<>();

        // Initialize the adapter with the context and Travel Post List
        adapter = new CommunityAdapter(getContext(), travelPosts);

        // Set up the ListView and attach the adapter
        ListView listView = view.findViewById(R.id.reservation_list);
        listView.setAdapter(adapter);

        // Find the ImageButton (add new post button) and set OnClickListener
        ImageButton addPostButton = view.findViewById(R.id.addPostButton);
        addPostButton.setOnClickListener(v -> showAddPostDialog());
    }

    // Method to show the dialog for adding a new post
    private void showAddPostDialog() {
        // Create a new AlertDialog.Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_new_travel_post, null);
        builder.setView(dialogView);

        // Get references to the EditText fields and button
        EditText startDateInput = dialogView.findViewById(R.id.startDateInput);
        EditText endDateInput = dialogView.findViewById(R.id.endDateInput);
        EditText destinationInput = dialogView.findViewById(R.id.destinationInput);
        EditText accommodationsInput = dialogView.findViewById(R.id.accommodationsInput);
        EditText diningReservationInput = dialogView.findViewById(R.id.diningReservationInput);
        EditText notesInput = dialogView.findViewById(R.id.notesInput);
        Button saveButton = dialogView.findViewById(R.id.button_add_post);

        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();

        // Set the save button action
        saveButton.setOnClickListener(v -> {
            // Get the input data
            String startDateStr = startDateInput.getText().toString();
            String endDateStr = endDateInput.getText().toString();
            String destination = destinationInput.getText().toString();
            String accommodations = accommodationsInput.getText().toString();
            String diningReservation = diningReservationInput.getText().toString();
            String notes = notesInput.getText().toString();

            // Parse the start and end date strings to LocalDate objects
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate startDate = LocalDate.parse(startDateStr, formatter);
            LocalDate endDate = LocalDate.parse(endDateStr, formatter);

            // Calculate the duration in days
            long duration = ChronoUnit.DAYS.between(startDate, endDate);

            // Create the new TravelCommunity object with the calculated duration
            TravelCommunity newPost = new TravelCommunity(
                    duration, destination, accommodations, diningReservation, notes);

            // Add the new post to the list and update the adapter
            travelPosts.add(newPost);
            adapter.notifyDataSetChanged();


            // Dismiss the dialog
            dialog.dismiss();
        });

    }


        dialog.show();
    }

}
