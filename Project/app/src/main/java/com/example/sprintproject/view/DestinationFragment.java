package com.example.sprintproject.view;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
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

    private DestinationViewModel destinationViewModel; // ViewModel for managing data
    private RecyclerView recyclerView; // RecyclerView to display destinations
    private DestinationAdapter adapter; // Adapter to bind data to RecyclerView
    private EditText etLocation, etStartDate, etEndDate; // Form fields

    private DestinationViewModel viewModel;
    private EditText locationInput, startDateInput, endDateInput;
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
        Button submitButton = rootView.findViewById(R.id.btn_submit);
        Button cancelButton = rootView.findViewById(R.id.btn_cancel);
        Button calculateVacationTimeButton = rootView.findViewById(R.id.btn_calculate_vacation);

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

