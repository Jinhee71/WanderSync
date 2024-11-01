package com.example.sprintproject.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.sprintproject.R;

import com.example.sprintproject.model.Dining;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DiningReservationAdapter extends BaseAdapter {
    private Context context;
    private List<Dining> reservations;

    public DiningReservationAdapter(Context context, List<Dining> reservations) {
        this.context = context;
        this.reservations = reservations;
    }

    @Override
    public int getCount() {
        return reservations.size();
    }

    @Override
    public Object getItem(int position) {
        return reservations.get(position);
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

        TextView location = convertView.findViewById(R.id.location);
        TextView restaurantName = convertView.findViewById(R.id.restaurant_name);
        TextView time = convertView.findViewById(R.id.time);
        TextView website = convertView.findViewById(R.id.website);

        // Setting data in views
        location.setText(reservation.getLocation());
        restaurantName.setText(reservation.getWebsite()); // Assuming website is the name of the restaurant in this context
        website.setText(reservation.getWebsite());

        // Format and set reservation time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        time.setText(reservation.getReservationTime().format(formatter));

        return convertView;
    }
}
