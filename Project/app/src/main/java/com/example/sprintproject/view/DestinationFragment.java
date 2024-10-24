package com.example.sprintproject.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.sprintproject.R;
import com.example.sprintproject.viewmodel.DestinationViewModel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class DestinationFragment extends Fragment {

    private DestinationViewModel viewModel;
    private EditText locationInput, startDateInput, endDateInput;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_destination, container, false);

        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(DestinationViewModel.class);

        // Find Views
        locationInput = rootView.findViewById(R.id.et_location);
        startDateInput = rootView.findViewById(R.id.et_start_date);
        endDateInput = rootView.findViewById(R.id.et_end_date);
        Button submitButton = rootView.findViewById(R.id.btn_submit);

        // Log Travel Button and Form
        Button logTravelButton = rootView.findViewById(R.id.btn_log_travel);
        LinearLayout formLayout = rootView.findViewById(R.id.form_log_travel);
        Button calculateVacationTimeButton = rootView.findViewById(R.id.btn_calculate_vacation);



        logTravelButton.setOnClickListener(v -> {
            if (formLayout.getVisibility() == View.GONE) {
                formLayout.setVisibility(View.VISIBLE);
            } else {
                formLayout.setVisibility(View.GONE);
            }
        });

        // Submit button click listener
        submitButton.setOnClickListener(v -> {
            String location = locationInput.getText().toString();
            String startDateText = startDateInput.getText().toString();
            String endDateText = endDateInput.getText().toString();

            if (location.isEmpty()) {
                Toast.makeText(getContext(), "Location cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                // Parse the inputted date strings into LocalDate
                LocalDate startDate = LocalDate.parse(startDateText, dateFormatter);
                LocalDate endDate = LocalDate.parse(endDateText, dateFormatter);

                // Perform date validation
                if (startDate.isAfter(endDate)) {
                    Toast.makeText(getContext(), "Start date cannot be after end date", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Submit the validated destination details
                boolean addedSuccessfully = viewModel.addDestination(location, startDate, endDate);
                if (addedSuccessfully) {
                    Toast.makeText(getContext(), "Destination added successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Error adding destination", Toast.LENGTH_SHORT).show();
                }
            } catch (DateTimeParseException e) {
                Toast.makeText(getContext(), "Invalid date format. Use yyyy-MM-dd.", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

}

