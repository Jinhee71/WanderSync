package com.example.sprintproject.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sprintproject.R;
import com.example.sprintproject.model.Destination;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class DestinationAdapter extends RecyclerView.Adapter<DestinationAdapter.DestinationViewHolder> {

    private List<Destination> destinationList;

    // Adapter constructor
    public DestinationAdapter(List<Destination> destinationList) {
        this.destinationList = destinationList;
    }

    @NonNull
    @Override
    public DestinationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.destination_item, parent, false);
        return new DestinationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DestinationViewHolder holder, int position) {
        Destination destination = destinationList.get(position);

        // Set destination location
        holder.destinationLocation.setText(destination.getDestinationLocation());

        // Calculate days planned
        LocalDate startDate = destination.getStartDate();
        LocalDate endDate = destination.getEndDate();
        long daysPlanned = ChronoUnit.DAYS.between(startDate, endDate);

        // Set the calculated days
        holder.daysPlanned.setText(daysPlanned + " days planned");
    }

    @Override
    public int getItemCount() {
        return destinationList.size();
    }

    public static class DestinationViewHolder extends RecyclerView.ViewHolder {
        public TextView destinationLocation;
        public TextView daysPlanned;

        public DestinationViewHolder(View itemView) {
            super(itemView);
            destinationLocation = itemView.findViewById(R.id.tv_destination_location);
            daysPlanned = itemView.findViewById(R.id.tv_days_planned);
        }
    }
}
