package com.example.sprintproject.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sprintproject.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;


import androidx.fragment.app.Fragment;

import com.example.sprintproject.R;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class LogisticsFragment extends Fragment {
    private PieChart pieChart;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;


    public LogisticsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_logistics, container, false);

        pieChart = view.findViewById(R.id.pieChart);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        Button displayDataButton = view.findViewById(R.id.displayDataButton);

        displayDataButton.setOnClickListener(this::onDisplayDataClick);

        return view;    }

    public void onDisplayDataClick(View v) {

        String userId = mAuth.getCurrentUser().getUid();
        db.collection("User").document(userId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        long allottedDays = documentSnapshot.contains("allocatedDays") ?
                                documentSnapshot.getLong("allocatedDays") : 0;

                        String tripId = documentSnapshot.getString("activeTrip");

                        db.collection("Trip").document(tripId).collection("Destination")
                                .get()
                                .addOnSuccessListener(queryDocumentSnapshots -> {
                                    long plannedDays = 0;
                                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                        LocalDate startDate = LocalDate.parse(document.getString("start date"));
                                        LocalDate endDate = LocalDate.parse(document.getString("end date"));

                                        plannedDays += ChronoUnit.DAYS.between(startDate, endDate);
                                        //Toast.makeText(getContext(), "length = " + ChronoUnit.DAYS.between(startDate, endDate), Toast.LENGTH_SHORT).show();

                                    }

                                    int remainingDays = (int) (allottedDays - plannedDays);
                                    if (remainingDays < 0) {
                                        remainingDays = 0;
                                    }

//                                    Output test
//                                    Toast.makeText(getContext(), "allocatedDays = " + allottedDays, Toast.LENGTH_SHORT).show();
//                                    Toast.makeText(getContext(), "plannedDays = " + plannedDays, Toast.LENGTH_SHORT).show();
//                                    Toast.makeText(getContext(), "remainingDays = " + remainingDays, Toast.LENGTH_SHORT).show();


                                    setupPieChart((int) plannedDays, remainingDays);
                                })
                                .addOnFailureListener(e -> {
                                    System.out.println("Error retrieving destinations: " + e.getMessage());
                                    e.printStackTrace();
                                });


                    } else {
                        Toast.makeText(getContext(), "User data not found", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                });
    }

    private void setupPieChart(int plannedDays, int remainingDays) {
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false); // Removes default description
        pieChart.setDrawHoleEnabled(false); // Disable the hole in the middle

        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(plannedDays, "Planned Days"));
        entries.add(new PieEntry(remainingDays, "Remaining Days"));

        PieDataSet dataSet = new PieDataSet(entries, "Trip Days");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        PieData pieData = new PieData(dataSet);
        pieChart.setData(pieData);
        pieChart.invalidate(); // Refreshes the chart

        Toast.makeText(getContext(), "Visualizing allotted vs planned trip days", Toast.LENGTH_SHORT).show();
    }


    public void onAddNoteButtonClick(View v) {
        // Add functionality for adding a note
    }

    public void addCollaboratorButtonClick(View v) {
        // Add functionality for adding a collaborator
    }
}