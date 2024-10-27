package com.example.sprintproject.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.sprintproject.R;
import com.example.sprintproject.viewmodel.DestinationViewModel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class DestinationFragment extends Fragment {

    private DestinationViewModel destinationViewModel; // ViewModel for managing data
    private RecyclerView recyclerView; // RecyclerView to display destinations
    private DestinationAdapter adapter; // Adapter to bind data to RecyclerView
    private EditText etLocation, etStartDate, etEndDate; // Form fields

    private DestinationViewModel viewModel;
    private EditText locationInput, startDateInput, endDateInput;
    private EditText uStartDateInput, uEndDateInput, uDuration;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private boolean isFormVisible = false;  // Tracks whether the Travel Log Form is visible
    private LinearLayout formLogTravel;     // Variable to store the Travel Log Form layout
    private LinearLayout popupLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_destination, container, false);

        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(DestinationViewModel.class);

        // Buttons and input views
        Button logTravelButton = rootView.findViewById(R.id.btn_log_travel);
        locationInput = rootView.findViewById(R.id.et_location);
        startDateInput = rootView.findViewById(R.id.et_start_date);
        endDateInput = rootView.findViewById(R.id.et_end_date);
        uStartDateInput = rootView.findViewById(R.id.et_start_date_popup);
        uEndDateInput = rootView.findViewById(R.id.et_end_date_popup);
        uDuration = rootView.findViewById(R.id.et_duration_popup);
        Button submitButton = rootView.findViewById(R.id.btn_submit);
        Button cancelButton = rootView.findViewById(R.id.btn_cancel);
        Button calculateVacationTimeButton = rootView.findViewById(R.id.btn_calculate_vacation);
        Button finalCalculate = rootView.findViewById(R.id.btn_calculate);
        //layouts
        formLogTravel = rootView.findViewById(R.id.form_log_travel);
        LinearLayout calculateAndListLayout = rootView.findViewById(R.id.calculateButton_and_list_layout);
        recyclerView = rootView.findViewById(R.id.rv_destinations);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        popupLayout = rootView.findViewById(R.id.popup_layout);


        // Observe the data from ViewModel
        //seems this is the problem
//        destinationViewModel.getDestinations().observe(getViewLifecycleOwner(), destinations -> {
//            // Initialize and set the adapter
//            adapter = new DestinationAdapter(destinations);
//            recyclerView.setAdapter(adapter);
//        });

        // Set the initial height of the Travel Log Form to 1dp
        //wanted to do 0dp but not worked.
        formLogTravel.getLayoutParams().height = 1;
        formLogTravel.requestLayout();  // Request a layout update

        // Set a click listener for the logTravelButton to show/hide the form
        logTravelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFormHeight();  // Call the method to toggle the form's height
            }
        });

        // Set a click listener for the cancelButton to hide the form
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFormHeight();  // Call the same method to toggle the form's height
            }
        });

        calculateVacationTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle visibility of the popup layout
                if (popupLayout.getVisibility() == View.GONE) {
                    popupLayout.setVisibility(View.VISIBLE);
                    Log.d("Visibility", "Popup Layout set to VISIBLE");
                } else {
                    popupLayout.setVisibility(View.GONE);
                    Log.d("Visibility", "Popup Layout set to GONE");
                }
                popupLayout.invalidate();
                popupLayout.requestLayout();
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

        finalCalculate.setOnClickListener(v -> {
            String startDateText = uStartDateInput.getText().toString();
            String endDateText = uEndDateInput.getText().toString();
            String durationText = uDuration.getText().toString();
            int counter = 0;
            if(!durationText.isEmpty()) {
                counter++;
            }
            if(!startDateText.isEmpty()) {
                counter++;
            }
            if(!endDateText.isEmpty()) {
                counter++;
            }
            if(counter < 2) {
                Toast.makeText(getContext(), "Invalid, please input at least 2 of the 3 fields", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                LocalDate startDate = null;
                LocalDate endDate = null;
                long duration =0;
                if(!startDateText.isEmpty()) {
                    startDate = LocalDate.parse(startDateText, dateFormatter);
                }
                if(!endDateText.isEmpty()) {
                    endDate = LocalDate.parse(endDateText, dateFormatter);
                }
                if(!durationText.isEmpty()) {
                    duration = Integer.parseInt(durationText);
                }

                if(counter ==3 && (startDate !=null && endDate != null)) {
                    if(duration != java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate)){
                        Toast.makeText(getContext(), "Invalid, please make duration and start/end dates match", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if(counter == 2) {
                    if (startDate != null && endDate != null && duration == 0) {
                        // Calculate duration if start and end dates are available
                        duration = java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate);
                    } else if (startDate != null && duration != 0 && endDate == null) {
                        // Calculate end date if start date and duration are available
                        endDate = startDate.plusDays(duration);
                    } else if (endDate != null && duration != 0 && startDate == null) {
                        // Calculate start date if end date and duration are available
                        startDate = endDate.minusDays(duration);
                    }
                }

                // Perform date validation
                assert startDate != null;
                if (startDate.isAfter(endDate)) {
                    Toast.makeText(getContext(), "Start date cannot be after end date", Toast.LENGTH_SHORT).show();
                    return;
                }
//                boolean addedSuccessfully = viewModel.updateAllocated(duration, startDate, endDate);
//                if (addedSuccessfully) {
//                    Toast.makeText(getContext(), "Updated Allocated Vacation Data Successfully", Toast.LENGTH_SHORT).show();
//
//                } else {
//                    Toast.makeText(getContext(), "Error updating allocated vacation data", Toast.LENGTH_SHORT).show();
//                }
            } catch (DateTimeParseException e) {
                Toast.makeText(getContext(), "Invalid format", Toast.LENGTH_SHORT).show();
            }
        });


        return rootView;
    }



    // Method to toggle the height of the Travel Log Form
    private void toggleFormHeight() {
        ViewGroup.LayoutParams params = formLogTravel.getLayoutParams();
        if (isFormVisible) {
            // Set the height to 1dp to hide the form
            params.height = 1;
        } else {
            // Set the height to wrap_content to show the form
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        }
        formLogTravel.setLayoutParams(params);  // Apply the changed layout parameters
        isFormVisible = !isFormVisible;  // Toggle the visibility state
    }

}

