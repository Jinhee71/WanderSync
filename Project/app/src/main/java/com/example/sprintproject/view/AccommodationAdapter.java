package com.example.sprintproject.view;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sprintproject.R;
import com.example.sprintproject.model.Accommodation;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AccommodationAdapter extends BaseAdapter {

    private Context context;
    private List<Accommodation> accommodations;

    public AccommodationAdapter(Context context, List<Accommodation> accommodations) {
        this.context = context;
        this.accommodations = accommodations;
    }

    @Override
    public int getCount() {
        return accommodations.size();
    }

    @Override
    public Object getItem(int position) {
        return accommodations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.accommodation_item, parent, false);
        }

        Accommodation accommodation = accommodations.get(position);

        // Get references to the TextViews
        TextView locationView = convertView.findViewById(R.id.location);
        TextView hotelNameView = convertView.findViewById(R.id.hotel_name);
        TextView checkInView = convertView.findViewById(R.id.check_in);
        TextView checkOutView = convertView.findViewById(R.id.check_out);
        TextView numOfRoomsView = convertView.findViewById(R.id.num_of_rooms);
        TextView roomTypeView = convertView.findViewById(R.id.room_type);

        // Set the text for the accommodation views
        locationView.setText(accommodation.getLocation());
        hotelNameView.setText(accommodation.getHotelName());
        checkInView.setText("Check-in: " + accommodation.getCheckIn());
        checkOutView.setText("Check-out: " + accommodation.getCheckOut());
        numOfRoomsView.setText("Number of Rooms: " + accommodation.getNumberOfRooms());
        roomTypeView.setText("Room Type: " + accommodation.getRoomType());

        // Check if the accommodation is expired and mark it red if necessary
        if (isPastDate(accommodation.getCheckOut())) {
            // Mark expired reservations with a red background
            convertView.setBackgroundColor(Color.RED); // Red color for expired accommodations
        } else {
            // Set a default background color (e.g., white) for non-expired accommodations
            convertView.setBackgroundColor(Color.WHITE); // Default color for valid reservations
        }

        return convertView;
    }

    // Method to check if an accommodation's checkout date is in the past
    private boolean isPastDate(String checkOut) {
        try {
            // Ensure the checkOut date is in the correct format ("yyyy-MM-dd HH:mm")
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime checkOutDate = LocalDateTime.parse(checkOut, formatter);

            // Get current time in UTC (to avoid any local time discrepancies)
            LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);

            Log.d("AccommodationAdapter", "Check-out: " + checkOutDate + " Now: " + now);


            // Compare the checkOut date with the current date and time
            return checkOutDate.isBefore(now);
        } catch (Exception e) {
            // Handle potential parsing issues (e.g., incorrect date format)
            return false;
        }
    }
}
