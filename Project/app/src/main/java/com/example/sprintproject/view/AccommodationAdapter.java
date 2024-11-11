package com.example.sprintproject.view;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sprintproject.R;
import com.example.sprintproject.model.Accommodation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AccommodationAdapter extends BaseAdapter {

    private Context context;
    private List<Accommodation> accommodations;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

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
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.accommodation_item, parent, false);
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

        LocalDateTime checkInTime = accommodation.getCheckinTime();
        String formattedCheckIn = checkInTime != null
                ? checkInTime.format(formatter) : "No Time Provided";
        checkInView.setText("Check-in: " + formattedCheckIn);

        boolean isPastDue = (checkInTime != null && checkInTime.isBefore(LocalDateTime.now()))
                || isPastDate(accommodation.getCheckOut());

        if (isPastDue) {
            convertView.setBackgroundColor(Color.LTGRAY); // Mark expired as light gray
        } else {
            convertView.setBackgroundColor(Color.WHITE); // Reset color for non-past due reservation
        }

        return convertView;
    }

    private boolean isPastDate(String dateTimeStr) {
        try {
            LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, formatter);
            return dateTime.isBefore(LocalDateTime.now());
        } catch (Exception e) {
            // Handle potential parsing issues (e.g., incorrect date format)
            return false;
        }
    }
}
