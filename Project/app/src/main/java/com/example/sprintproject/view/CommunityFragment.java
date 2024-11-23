package com.example.sprintproject.view;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sprintproject.R;
import com.example.sprintproject.model.TravelCommunity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class CommunityFragment extends Fragment {

    public CommunityFragment() { }

    private List<TravelCommunity> travelPosts;
    private CommunityAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_community, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Initiate the Travel Post List
        travelPosts = new ArrayList<>();

        //Initialize the adapter with the context and Travel Post List
        adapter = new CommunityAdapter(getContext(), travelPosts);

        //set up the ListView and attach the adapter
        ListView listView = view.findViewById(R.id.reservation_list);
        listView.setAdapter(adapter);

        //Find the imageButton(add new post button) and set OnClickListener
        ImageButton addPostButton = view.findViewById(R.id.addPostButton);
        addPostButton.setOnClickListener(v -> showAddPostDialog());
    }


    private void showAddPostDialog() {
        //create a new AlertDialog.Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        // Inflate the custom layout for the dialog
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_new_travel_post, null);
        builder.setView(dialogView);

        // Create and show the dialog
        AlertDialog dialog = builder.create();

        dialog.show();

    }
}