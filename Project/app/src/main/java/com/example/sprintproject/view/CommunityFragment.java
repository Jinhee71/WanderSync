package com.example.sprintproject.view;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.example.sprintproject.model.TravelCommunity;
import com.example.sprintproject.viewmodel.TravelCommunityViewModel;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class CommunityFragment extends Fragment {

    private List<TravelCommunity> travelPosts;
    private CommunityAdapter adapter;
    private TravelCommunityViewModel travelCommunityViewModel;

    public CommunityFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_community, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        travelPosts = new ArrayList<>();
        adapter = new CommunityAdapter(getContext(), travelPosts);

        ListView listView = view.findViewById(R.id.reservation_list);
        listView.setAdapter(adapter);

        travelCommunityViewModel = new TravelCommunityViewModel();

        // Fetch posts from ViewModel
        travelCommunityViewModel.fetchTravelCommunities(posts -> {
            travelPosts.clear();
            travelPosts.addAll(posts);
            adapter.notifyDataSetChanged();
        });

        ImageButton addPostButton = view.findViewById(R.id.addPostButton);
        addPostButton.setOnClickListener(v -> showAddPostDialog());
    }

    private void showAddPostDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_new_travel_post, null);
        builder.setView(dialogView);

        EditText startDateInput = dialogView.findViewById(R.id.startDateInput);
        EditText endDateInput = dialogView.findViewById(R.id.endDateInput);
        EditText destinationInput = dialogView.findViewById(R.id.destinationInput);
        EditText accommodationsInput = dialogView.findViewById(R.id.accommodationsInput);
        EditText diningReservationInput = dialogView.findViewById(R.id.diningReservationInput);
        EditText notesInput = dialogView.findViewById(R.id.notesInput);
        Button saveButton = dialogView.findViewById(R.id.savePostButton);

        AlertDialog dialog = builder.create();
        dialog.show();

        saveButton.setOnClickListener(v -> {
            try {
                String startDateStr = startDateInput.getText().toString().trim();
                String endDateStr = endDateInput.getText().toString().trim();
                String destination = destinationInput.getText().toString().trim();
                String accommodations = accommodationsInput.getText().toString().trim();
                String diningReservation = diningReservationInput.getText().toString().trim();
                String notes = notesInput.getText().toString().trim();

                if (TextUtils.isEmpty(startDateStr) || TextUtils.isEmpty(endDateStr)
                        || TextUtils.isEmpty(destination)) {
                    throw new IllegalArgumentException("Start Date, End Date, and Destination "
                            + "are required.");
                }

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate startDate = LocalDate.parse(startDateStr, formatter);
                LocalDate endDate = LocalDate.parse(endDateStr, formatter);

                if (endDate.isBefore(startDate)) {
                    throw new IllegalArgumentException("End Date must be after Start Date.");
                }

                long duration = ChronoUnit.DAYS.between(startDate, endDate);

                // Generate timestamp
                long timestamp = Instant.now().toEpochMilli();

                TravelCommunity newPost = new TravelCommunity(duration, destination,
                        accommodations, diningReservation, notes);
                travelPosts.add(newPost);
                adapter.notifyDataSetChanged();

                travelCommunityViewModel.addTravelCommunity(duration, destination,
                        accommodations, diningReservation, notes);

                dialog.dismiss();
            } catch (Exception e) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Invalid Input")
                        .setMessage(e.getMessage())
                        .setPositiveButton("OK", null)
                        .show();
            }
        });
    }
}
