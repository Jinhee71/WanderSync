package com.example.sprintproject.view;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sprintproject.R;
import com.example.sprintproject.model.Accommodation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AccommodationFragment extends Fragment {

    private List<Accommodation> accommodations;
    private AccommodationAdapter adapter;

    public AccommodationFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_accommodation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        accommodations = new ArrayList<>();
        adapter = new AccommodationAdapter(getContext(), accommodations);

        ListView listView = view.findViewById(R.id.accommodation_list);
        listView.setAdapter(adapter);

        ImageButton addAccommodationButton = view.findViewById(R.id.addAccommodationButton);
        addAccommodationButton.setOnClickListener(v -> showAddAccommodationDialog());
    }

    private void showAddAccommodationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_accommodation, null);
        builder.setView(dialogView);

        EditText checkInEdit = dialogView.findViewById(R.id.check_in);
        EditText checkOutEdit = dialogView.findViewById(R.id.check_out);
        EditText locationEdit = dialogView.findViewById(R.id.location);
        Spinner numOfRoomsSpinner = dialogView.findViewById(R.id.num_of_rooms); // Changed from EditText to Spinner
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
            int numOfRooms = Integer.parseInt(numOfRoomsSpinner.getSelectedItem().toString()); // Parse selected item from Spinner

            // Parse check-in and check-out dates (consider adding validation)
            LocalDateTime checkIn = LocalDateTime.parse(checkInEdit.getText().toString());
            LocalDateTime checkOut = LocalDateTime.parse(checkOutEdit.getText().toString());

            accommodations.add(new Accommodation(location, "Hotel Name", checkIn, checkOut, numOfRooms, roomType));
            adapter.notifyDataSetChanged();
            dialog.dismiss();
        });

        dialog.show();
    }
}