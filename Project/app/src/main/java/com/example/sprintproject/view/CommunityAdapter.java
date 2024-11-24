package com.example.sprintproject.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.sprintproject.R;
import com.example.sprintproject.model.TravelCommunity;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class CommunityAdapter extends BaseAdapter {
    private Context context;
    private List<TravelCommunity> posts;

    public CommunityAdapter(Context context, List<TravelCommunity> posts) {
        this.context = context;
        this.posts = posts;
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Object getItem(int position) {
        return posts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_travel_community,
                    parent, false);
        }

        // Get the current travel post
        TravelCommunity travelPost = posts.get(position);

        // Set up the views
        TextView usernameView = convertView.findViewById(R.id.username);
        TextView destinationView = convertView.findViewById(R.id.destination);
        TextView durationView = convertView.findViewById(R.id.duration);
        ImageView arrowView = convertView.findViewById(R.id.arrowIcon);

        usernameView.setText(travelPost.getUsername());

        destinationView.setText(travelPost.getDestination());

        durationView.setText("Duration: " + travelPost.getDuration() + " days");

        arrowView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_arrow_right));

        arrowView.setOnClickListener(v -> {
            Toast.makeText(context, "Navigating to: ", Toast.LENGTH_SHORT).show();
            // Create and display the BottomSheetDialog
            View bottomSheetView = LayoutInflater.from(context).inflate(R.layout.trip_info, null);

            // Use different variable names for Bottom Sheet
            TextView bottomSheetDestinationView = bottomSheetView.findViewById(R.id.destination_text);
            TextView bottomSheetDurationView = bottomSheetView.findViewById(R.id.duration_text);
            TextView bottomSheetAccView = bottomSheetView.findViewById(R.id.accomodation_text);
            TextView bottomSheetDineView = bottomSheetView.findViewById(R.id.dining_text);
            TextView bottomSheetTView = bottomSheetView.findViewById(R.id.transportation_text);
            TextView bottomSheetNotesView = bottomSheetView.findViewById(R.id.notes_text);

            // Populate the BottomSheet with data
            bottomSheetDestinationView.setText(travelPost.getDestination());
            bottomSheetDurationView.setText("Duration: " + travelPost.getDuration() + " days");
            bottomSheetAccView.setText("Accomodation: " + travelPost.getAccommodations());
            bottomSheetDineView.setText("Dining: "+travelPost.getDiningReservations());
            bottomSheetTView.setText("Default Transportation");
            bottomSheetNotesView.setText("Notes: "+travelPost.getNotes());

            // Build and show the BottomSheetDialog
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
            bottomSheetDialog.setContentView(bottomSheetView);
            bottomSheetDialog.show();
        });
        return convertView;
    }
}
