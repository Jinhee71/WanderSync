package com.example.sprintproject.view;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.sprintproject.R;
import com.example.sprintproject.model.TravelCommunity;
import com.example.sprintproject.model.TravelCommunityFactory;
import com.example.sprintproject.viewmodel.CommunityViewModel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class CommunityFragment extends Fragment {

    private List<TravelCommunity> travelPosts;
    private CommunityAdapter adapter;
    private CommunityViewModel communityViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_community, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize the ViewModel
        communityViewModel = new ViewModelProvider(this).get(CommunityViewModel.class);

        // Initialize the adapter
        travelPosts = new ArrayList<>();
        adapter = new CommunityAdapter(getContext(), travelPosts);

        // Set up the ListView with the adapter
        ListView listView = view.findViewById(R.id.reservation_list);
        listView.setAdapter(adapter);

        // Observe changes in the travel posts from the ViewModel
        communityViewModel.getTravelPosts().observe(getViewLifecycleOwner(), newPosts -> {
            // Update the adapter with new posts
            travelPosts.clear();
            travelPosts.addAll(newPosts);
            adapter.notifyDataSetChanged();
        });

        // Handle the button for adding new posts
        ImageButton addPostButton = view.findViewById(R.id.addPostButton);
        addPostButton.setOnClickListener(v -> showAddPostDialog());
    }

    private void showAddPostDialog() {
        // Create an AlertDialog for adding a new post
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_new_travel_post, null);
        builder.setView(dialogView);

        // Create the dialog
        AlertDialog dialog = builder.create();

        // Set up the button for saving the post
        dialogView.findViewById(R.id.savePostButton).setOnClickListener(v -> {
            String destination = ((EditText) dialogView.findViewById(R.id.destinationInput)).getText().toString();
            String startDateString = ((EditText) dialogView.findViewById(R.id.startDateInput)).getText().toString();
            String endDateString = ((EditText) dialogView.findViewById(R.id.endDateInput)).getText().toString();

            // Convert the input strings to LocalDate or LocalDateTime
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            LocalDate startDate = LocalDate.parse(startDateString, formatter);
            LocalDate endDate = LocalDate.parse(endDateString, formatter);

            // Calculate the duration in days
            long durationDays = ChronoUnit.DAYS.between(startDate, endDate);

            String duration = String.valueOf(durationDays);

            // Create a new TravelCommunity post using the factory method
            TravelCommunity newPost = TravelCommunityFactory.createPost(destination, duration);

            // Add the new post to Firestore
            communityViewModel.addNewPost(newPost);

            // Dismiss the dialog
            dialog.dismiss();
        });

        dialog.show();
    }

}
