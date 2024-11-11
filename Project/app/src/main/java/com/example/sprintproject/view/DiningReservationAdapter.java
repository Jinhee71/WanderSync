package com.example.sprintproject.view;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.example.sprintproject.R;
import com.example.sprintproject.model.Dining;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DiningReservationAdapter extends BaseAdapter {
    private final Context context;
    private List<Dining> reservations;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public DiningReservationAdapter(Context context, List<Dining> reservations) {
        this.context = context;
        this.reservations = reservations;
    }

    @Override
    public int getCount() {
        return reservations != null ? reservations.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return reservations != null ? reservations.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.dining_reservation_item, parent, false);
        }

        Dining reservation = reservations.get(position);

        TextView locationTextView = convertView.findViewById(R.id.location);
        TextView restaurantNameTextView = convertView.findViewById(R.id.restaurant_name);
        TextView timeTextView = convertView.findViewById(R.id.time);
        TextView websiteTextView = convertView.findViewById(R.id.website);

        String location = reservation.getLocation() != null ? reservation.getLocation() : "Unknown Location";
        locationTextView.setText(location);

        String restaurantName = reservation.getWebsite() != null ? reservation.getWebsite() : "No Website Provided";
        restaurantNameTextView.setText(restaurantName);

        websiteTextView.setText(reservation.getWebsite() != null ? reservation.getWebsite() : "N/A");

        LocalDateTime reservationTime = reservation.getReservationTime();
        String formattedTime = reservationTime != null ? reservationTime.format(formatter) : "No Time Provided";
        timeTextView.setText(formattedTime);

        if (reservationTime != null && reservationTime.isBefore(LocalDateTime.now())) {
            convertView.setBackgroundColor(Color.LTGRAY); // Mark expired as light gray
        } else {
            convertView.setBackgroundColor(Color.WHITE); // Reset color for non-past due reservations
        }


        return convertView;
    }

    public void updateData(List<Dining> newReservations) {
        if (newReservations != null) {
            this.reservations = newReservations;
            notifyDataSetChanged();
        } else {
            Toast.makeText(context, "Failed to update dining reservations", Toast.LENGTH_SHORT).show();
        }
    }

    public void sortReservationsByDate() {
        if (reservations != null) {
            Collections.sort(reservations, new Comparator<Dining>() {
                @Override
                public int compare(Dining o1, Dining o2) {
                    return o1.getReservationTime().compareTo(o2.getReservationTime());
                }
            });
            notifyDataSetChanged();
        }
    }





}
