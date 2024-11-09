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
import androidx.lifecycle.ViewModelProvider;

import com.example.sprintproject.R;
import com.example.sprintproject.model.Dining;
import com.example.sprintproject.viewmodel.DiningViewModel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DiningFragment extends Fragment {

    private DiningViewModel diningViewModel;
    private DiningReservationAdapter adapter;

    public DiningFragment() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dining, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize ViewModel
        diningViewModel = new ViewModelProvider(this).get(DiningViewModel.class);

        // Set up the adapter with an empty list initially
        adapter = new DiningReservationAdapter(getContext(), new ArrayList<>());

        // Set up the ListView and attach the adapter
        ListView listView = view.findViewById(R.id.reservation_list);
        listView.setAdapter(adapter);

        // Load dining reservations and update the adapter
        loadDiningReservations();

        // Find the add button and set its click listener
        ImageButton addDiningButton = view.findViewById(R.id.addDiningButton);
        addDiningButton.setOnClickListener(v -> showAddReservationDialog());
    }

    private void loadDiningReservations() {
        diningViewModel.fetchDiningReservations(new DiningViewModel.FetchCallback() {
            @Override
            public void onFetchComplete(List<Dining> diningReservations) {
                // Update adapter with fetched data
                adapter.updateData(diningReservations);
            }
        });
    }

    private void showAddReservationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_reservation, null);
        builder.setView(dialogView);

        EditText editTime = dialogView.findViewById(R.id.edit_time);
        EditText editLocation = dialogView.findViewById(R.id.edit_location);
        EditText editWebsite = dialogView.findViewById(R.id.edit_website);
        Button addReservationButton = dialogView.findViewById(R.id.button_add_reservation);

        AlertDialog dialog = builder.create();

        addReservationButton.setOnClickListener(v -> {
            String timeInput = editTime.getText().toString();
            String location = editLocation.getText().toString();
            String website = editWebsite.getText().toString();

            LocalDateTime reservationTime;
            try {
                reservationTime = LocalDateTime.parse(timeInput);
            } catch (Exception e) {
                reservationTime = LocalDateTime.now();
            }

            diningViewModel.addDining(website, location, reservationTime, 5); // Placeholder for review

            dialog.dismiss();
            loadDiningReservations(); // Reload reservations after adding a new one
        });

        dialog.show();
    }
}