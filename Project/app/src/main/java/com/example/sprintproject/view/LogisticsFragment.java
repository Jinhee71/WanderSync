package com.example.sprintproject.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;

import com.example.sprintproject.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class LogisticsFragment extends Fragment {
    private PieChart pieChart;
    private LinearLayout collaboratorsLayout;
    private TextView plannedDaysText;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    public LogisticsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_logistics, container, false);

        pieChart = view.findViewById(R.id.pieChart);
        plannedDaysText = view.findViewById(R.id.plannedDaysText);
        collaboratorsLayout = view.findViewById(R.id.collaboratorsLayout);
        Button displayDataButton = view.findViewById(R.id.displayDataButton);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        displayDataButton.setOnClickListener(v -> onDisplayDataClick());
        fetchAndRenderCollaboratorsFromDB();

        return view;
    }

    public void onDisplayDataClick() {
        String userId = mAuth.getCurrentUser().getUid();
        db.collection("User").document(userId).get().addOnSuccessListener(userDoc -> {
            if (userDoc.exists()) {
                String tripId = userDoc.getString("activeTrip");
                db.collection("Trip").document(tripId).get().addOnSuccessListener(tripDoc -> {
                    if (tripDoc.exists()) {
                        long plannedDays = tripDoc.contains("duration") ? tripDoc.getLong("duration") : 0;
                        long allottedDays = userDoc.contains("allocatedDays") ? userDoc.getLong("allocatedDays") : 0;
                        int remainingDays = Math.max(0, (int) (allottedDays - plannedDays));
                        plannedDaysText.setText("Planned Days: " + plannedDays);
                        setupPieChart((int) plannedDays, remainingDays);
                    }
                }).addOnFailureListener(e -> showToast("Error retrieving trip data"));
            }
        }).addOnFailureListener(e -> showToast("Error retrieving user data"));
    }


    private void setupPieChart(int plannedDays, int remainingDays) {
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawHoleEnabled(false);

        List<PieEntry> entries = Arrays.asList(
                new PieEntry(plannedDays, "Planned Days"),
                new PieEntry(remainingDays, "Remaining Days")
        );

        PieDataSet dataSet = new PieDataSet(entries, "Trip Days");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        pieChart.setData(new PieData(dataSet));
        pieChart.invalidate();

        showToast("Visualizing allotted vs planned trip days");
    }

    private void fetchAndRenderCollaboratorsFromDB() {
        String userId = mAuth.getCurrentUser().getUid();
        db.collection("User").document(userId).get().addOnSuccessListener(userDoc -> {
            if (userDoc.exists()) {
                String activeTrip = userDoc.getString("activeTrip");
                if (activeTrip != null) {
                    fetchCollaboratorsForTrip(activeTrip);
                }
            }
        }).addOnFailureListener(e -> showToast("Error fetching collaborators"));
    }

    private void fetchCollaboratorsForTrip(String tripId) {
        db.collection("Trip").document(tripId).get().addOnSuccessListener(tripDoc -> {
            if (tripDoc.exists()) {
                List<String> userIds = (List<String>) tripDoc.get("User IDs");
                if (userIds != null) {
                    fetchCollaboratorEmails(userIds);
                }
            }
        }).addOnFailureListener(e -> showToast("Error fetching trip collaborators"));
    }

    private void fetchCollaboratorEmails(List<String> userIds) {
        List<String> emails = new ArrayList<>();
        for (String userId : userIds) {
            db.collection("User").document(userId).get().addOnSuccessListener(userDoc -> {
                String email = userDoc.getString("email");
                if (email != null) {
                    emails.add(email);
                    if (emails.size() == userIds.size()) {
                        renderCollaborators(userIds, emails);
                    }
                }
            }).addOnFailureListener(e -> Log.e("LogisticsFragment", "Error fetching collaborator email", e));
        }
    }

    private void renderCollaborators(List<String> collaboratorUIDs,
                                     List<String> collaboratorEmails) {
        collaboratorsLayout.removeAllViews(); // Clear previous views if any
        for (int i = 0; i < collaboratorEmails.size(); i++) {
            String collaborator = collaboratorEmails.get(i);
            String collaboratorUID = collaboratorUIDs.get(i);

            Button collaboratorButton = new Button(getContext());
            collaboratorButton.setText(collaborator);
            collaboratorButton.setId(View.generateViewId()); // Generate a unique ID
            collaboratorButton.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));

            // Pass the correct UID to displayCollaboratorNotes
            collaboratorButton.setOnClickListener(v -> {
                // Handle button click
                displayCollaboratorNotes(collaborator, collaboratorUID);
            });

            collaboratorsLayout.addView(collaboratorButton); // Add Button to LinearLayout
        }
    }


    private void displayCollaboratorNotes(String userId, String email) {
        db.collection("User").document(userId).get().addOnSuccessListener(userDoc -> {
            List<String> notes = (List<String>) userDoc.get("notes");
            StringBuilder notesText = new StringBuilder("Notes for " + email + ":\n");
            if (notes != null) {
                for (String note : notes) {
                    notesText.append(" - ").append(note).append("\n");
                }
            }
            new AlertDialog.Builder(getContext())
                    .setTitle("Collaborator Notes")
                    .setMessage(notesText.toString())
                    .setPositiveButton("OK", null)
                    .show();
        }).addOnFailureListener(e -> showToast("Error retrieving collaborator notes"));
    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    // Add functionality for adding a note
    public void onAddNoteButtonClick(View v) {
        EditText input = new EditText(getContext());
        input.setHint("Enter your note");

        new AlertDialog.Builder(getContext())
                .setTitle("Add Note")
                .setView(input)
                .setPositiveButton("Add", (dialog, which) -> {
                    String note = input.getText().toString();
                    if (!note.isEmpty()) {
                        addNoteToDB(note);
                    } else {
                        showToast("Note cannot be empty!");
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void addNoteToDB(String note) {
        String currentUserUID = mAuth.getCurrentUser().getUid();

        // Get the current user document from Firestore
        db.collection("User")
                .whereEqualTo("authUID", currentUserUID)  // Ensure you're querying by the correct field
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Log the document data for debugging
                            Log.d("LogisticsFragment", document.getId() + " => " + document.getData());

                            // Retrieve existing notes or initialize if empty
                            ArrayList<String> userNotes = (ArrayList<String>) document.get("notes");
                            if (userNotes == null) {
                                userNotes = new ArrayList<>();
                            }

                            // Add the new note
                            userNotes.add(note);

                            // Update the 'notes' field in Firestore
                            document.getReference().update("notes", userNotes)
                                    .addOnCompleteListener(updateTask -> {
                                        if (updateTask.isSuccessful()) {
                                            Log.d("LogisticsFragment", "Successfully added user note");
                                            Toast.makeText(getContext(), "New note added: " + note, Toast.LENGTH_SHORT).show();
                                        } else {
                                            Log.d("Add Note", "Error updating user note: ", updateTask.getException());
                                        }
                                    });
                        }
                    } else {
                        Log.d("Add Note", "Error getting documents: ", task.getException());
                    }
                });
    }


    // Add collaborator by email
    public void addCollaboratorButtonClick(View v) {
        EditText input = new EditText(getContext());
        input.setHint("Enter collaborator's email");

        new AlertDialog.Builder(getContext())
                .setTitle("Add Collaborator")
                .setView(input)
                .setPositiveButton("Add", (dialog, which) -> {
                    String collaboratorEmail = input.getText().toString();
                    if (!collaboratorEmail.isEmpty()) {
                        addCollaboratorToDB(collaboratorEmail);
                    } else {
                        showToast("Email cannot be empty!");
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void addCollaboratorToDB(String email) {
        db.collection("User").whereEqualTo("email", email).get()
                .addOnSuccessListener(querySnapshot -> {
                    for (QueryDocumentSnapshot document : querySnapshot) {
                        String collaboratorId = document.getString("authUID");
                        String activeTrip = document.getString("activeTrip");
                        if (collaboratorId != null && activeTrip != null) {
                            addCollaboratorToTrip(collaboratorId, activeTrip);
                        }
                    }
                }).addOnFailureListener(e -> showToast("Error adding collaborator"));
    }

    private void addCollaboratorToTrip(String collaboratorId, String activeTripId) {
        db.collection("Trip").document(activeTripId).get().addOnSuccessListener(tripDoc -> {
            if (tripDoc.exists()) {
                List<String> userIds = (List<String>) tripDoc.get("User IDs");
                if (userIds == null) userIds = new ArrayList<>();
                userIds.add(collaboratorId);
                tripDoc.getReference().update("User IDs", userIds).addOnSuccessListener(aVoid ->
                                fetchAndRenderCollaboratorsFromDB())
                        .addOnFailureListener(e -> showToast("Error adding collaborator to trip"));
            }
        }).addOnFailureListener(e -> showToast("Error fetching trip"));
    }
}
