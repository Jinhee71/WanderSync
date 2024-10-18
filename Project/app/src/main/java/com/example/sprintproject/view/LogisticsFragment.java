package com.example.sprintproject.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.sprintproject.R;

public class LogisticsFragment extends Fragment {

    public LogisticsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_logistics, container, false);
    }

    public void onDisplayDataClick(View v) {
        // TODO: visualize the allotted vs planned trip days
        // in some way (perhaps a pie chart showing how many allotted days are being used as a
        // percentage).

        // YASHA
    }

    public void onAddNoteButtonClick(View v) {
        // Add functionality for adding a note
    }

    public void addCollaboratorButtonClick(View v) {
        // Add functionality for adding a collaborator
    }
}