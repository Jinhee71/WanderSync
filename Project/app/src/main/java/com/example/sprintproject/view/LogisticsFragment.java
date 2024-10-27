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
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;

import com.example.sprintproject.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;


import androidx.fragment.app.Fragment;

import com.example.sprintproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

<<<<<<< HEAD
import java.lang.reflect.Array;
=======
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

>>>>>>> e4c076c17b8807357e410182ccb4d0538f8c8e30
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Objects;

public class LogisticsFragment extends Fragment {
    private PieChart pieChart;
<<<<<<< HEAD
    private DatabaseReference mDatabase;
    private LinearLayout collaboratorsLayout; // Add this line

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
=======
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

>>>>>>> e4c076c17b8807357e410182ccb4d0538f8c8e30

    public LogisticsFragment() {
        // Required empty public constructor
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_logistics, container, false);

        pieChart = view.findViewById(R.id.pieChart);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        Button displayDataButton = view.findViewById(R.id.displayDataButton);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        displayDataButton.setOnClickListener(this::onDisplayDataClick);

        collaboratorsLayout = view.findViewById(R.id.collaboratorsLayout);


        fetchAndRenderCollaboratorsFromDB();

        return view;
    }

    private void renderCollaborators(ArrayList<String> collaboratorUIDs, ArrayList<String> collaboratorEmails) {
        collaboratorsLayout.removeAllViews(); // Clear previous views if any
        for (int i = 0; i < collaboratorUIDs.size(); i++) {
            String collaborator = collaboratorEmails.get(i);
            String collaboratorUID = collaboratorUIDs.get(i);
            Button collaboratorButton = new Button(getContext());
            collaboratorButton.setText(collaborator);
            collaboratorButton.setId(View.generateViewId()); // Generate a unique ID
            collaboratorButton.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            collaboratorButton.setOnClickListener(v -> {
                // Handle button click
                displayCollaboratorNotes(v, collaborator, collaboratorUID);
            });
            collaboratorsLayout.addView(collaboratorButton); // Add Button to LinearLayout
        }
    }

    public void onDisplayDataClick(View v) {

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false); // Removes default description
        pieChart.setDrawHoleEnabled(false); // Disable the hole in the middle

        int allottedDays = 10;  // Example data, should be fetched dynamically
        int plannedDays = 7;    // Example data, should be fetched dynamically

        int remainingDays = allottedDays - plannedDays;
        if (remainingDays < 0) {
            remainingDays = 0;
        }

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

//    private int fetchAllocatedDays(FirebaseAuth user) {
//        String userId = user.getCurrentUser().getUid();
//
//        db.collection("User").document(userId).get()
//                .addOnSuccessListener(documentSnapshot -> {
//                    if (documentSnapshot.exists()) {
//                        long allottedDays = documentSnapshot.contains("allocatedDays") ?
//                                documentSnapshot.getLong("allocatedDays") : 0;
//                    } else {
//                        Toast.makeText(getContext(), "User data not found", Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//    }


    public void onAddNoteButtonClick(View v) {
        // * Add functionality for adding a note

        // Display popup with input for new note
        // Create an EditText for user input
        final EditText input = new EditText(getContext());
        input.setHint("Enter your note");

        // Create the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add Note")
                .setView(input)
                .setPositiveButton("Add", (dialog, which) -> {
                    String note = input.getText().toString();
                    if (!note.isEmpty()) {
                        // Here you can add the note to the database
                        // Example: mDatabase.child("notes").push().setValue(note);
                        addNoteToDB(note);
                        Toast.makeText(getContext(), "New note added: " + note, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Note cannot be empty!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.cancel())
                .setCancelable(true);

        // Show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void addCollaboratorButtonClick(View v) {
        // * Add functionality for adding a collaborator

        // Display popup with input for new collaborator username/email
        final EditText input = new EditText(getContext());
        input.setHint("Enter collaborator's email");

        // Create the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add Collaborator")
                .setView(input)
                .setPositiveButton("Add", (dialog, which) -> {
                    String collaborator = input.getText().toString();
                    if (!collaborator.isEmpty()) {
                        // Here you can add the note to the database
                        // Example: mDatabase.child("notes").push().setValue(note);
                        addCollaboratorToDB(collaborator);
                        Toast.makeText(getContext(), "New collaborator added: " + collaborator, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Username/email cannot be empty!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.cancel())
                .setCancelable(true);

        // Show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();

        fetchAndRenderCollaboratorsFromDB();
    }

    private void displayCollaboratorNotes(View v, String username, String uid) {
        ArrayList<String> notesList = new ArrayList<>();

        DocumentReference currUserRef = db.collection("User").document(uid);

        currUserRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("LogisticsFragment", "DocumentSnapshot data: " + document.getData());
                        Log.d("LogisticsFragment", "DocumentSnapshot activeTrip: " + document.getString("activeTrip"));

                        ArrayList<String> docNotes = (ArrayList<String>) document.get("notes");

                        if (docNotes != null) {
                            notesList.addAll(docNotes);
                        }
                    } else {
                        Log.d("LogisticsFragment", "No such document");
                    }
                } else {
                    Log.d("LogisticsFragment", "get failed with ", task.getException());
                }
            }
        });


        // fetch user's notes for this trip
        String notes = "Notes for " + username + ": \n" + String.join("\n - ", notesList); // test temp

        // display notes
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Collaborator Notes")
                .setMessage(notes)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .setCancelable(true);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void fetchAndRenderCollaboratorsFromDB() {
        ArrayList<String> currCollaborators = new ArrayList<>();

        DocumentReference currUserRef = db.collection("User").document(mAuth.getCurrentUser().getUid());

        currUserRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("LogisticsFragment", "DocumentSnapshot data: " + document.getData());
                        Log.d("LogisticsFragment", "DocumentSnapshot activeTrip: " + document.getString("activeTrip"));

                        getTripCollaborators(document.getString("activeTrip"), currCollaborators);
                    } else {
                        Log.d("LogisticsFragment", "No such document");
                    }
                } else {
                    Log.d("LogisticsFragment", "get failed with ", task.getException());
                }
            }
        });
    }

    private void getTripCollaborators(String activeTrip, ArrayList<String> out) {
        DocumentReference currUserRef = db.collection("Trip").document(activeTrip);

        currUserRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("LogisticsFragment", "DocumentSnapshot data: " + document.getData());

                        ArrayList<String> userIDs = (ArrayList<String>) document.get("User IDs");

                        out.addAll(userIDs);

                        Log.d("LogisticsFragment", "DocumentSnapshot data OUT ARRAAY: " + out);

                        getEmailsFromUIDs(out);
//                        renderCollaborators(out);

                    } else {
                        Log.d("LogisticsFragment", "No such document");
                    }
                } else {
                    Log.d("LogisticsFragment", "get failed with ", task.getException());
                }
            }
        });

    }


    private void getEmailsFromUIDs(ArrayList<String> uidList) {
        ArrayList<String> userEmails = new ArrayList<>();

        String lastRef = uidList.get(uidList.size() - 1);

        for (int i = 0; i < uidList.size(); i++) {
            String currRef = uidList.get(i);

            DocumentReference currUserRef = db.collection("User").document(currRef);

            currUserRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Log.d("LogisticsFragment", "DocumentSnapshot data: " + document.getData());
                            Log.d("LogisticsFragment", "DocumentSnapshot activeTrip: " + document.getString("activeTrip"));

                            userEmails.add(document.getString("email"));

                            if (Objects.equals(currRef, lastRef)) {
                                renderCollaborators(uidList, userEmails);
                            }
                        } else {
                            Log.d("LogisticsFragment", "No such document");
                        }
                    } else {
                        Log.d("LogisticsFragment", "get failed with ", task.getException());
                    }
                }
            });

        }
    }


    private void addCollaboratorToDB(String email) {
        DocumentReference currUserRef = db.collection("User").document(mAuth.getCurrentUser().getUid());

        currUserRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("LogisticsFragment", "DocumentSnapshot data: " + document.getData());
                        Log.d("LogisticsFragment", "DocumentSnapshot activeTrip: " + document.getString("activeTrip"));

                        updateCollaborator(email, document.getString("activeTrip"));
                    } else {
                        Log.d("LogisticsFragment", "No such document");
                    }
                } else {
                    Log.d("LogisticsFragment", "get failed with ", task.getException());
                }
            }
        });
    }

    private void updateCollaborator(String email, String activeTripId) {
        db.collection("User")
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("LogisticsFragment", document.getId() + " => " + document.getData());
                                document.getReference().update("activeTrip", activeTripId)
                                        .addOnCompleteListener(updateTask -> {
                                            if (updateTask.isSuccessful()) {
                                                Log.d("LogisticsFragment", "Successfully updated collaborator's activeTrip");
                                            } else {
                                                Log.d("LogisticsFragment", "Error updating activeTrip: ", updateTask.getException());
                                            }
                                        });
                            }
                        } else {
                            Log.d("LogisticsFragment", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }



    private void addNoteToDB(String note) {
        db.collection("User")
                .whereEqualTo("email", mAuth.getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("LogisticsFragment", document.getId() + " => " + document.getData());
                                ArrayList<String> userNotes = (document.get("notes") == null) ? (new ArrayList<>()) : (ArrayList<String>) document.get("notes");
                                userNotes.add(note);

                                document.getReference().update("notes", userNotes)
                                        .addOnCompleteListener(updateTask -> {
                                            if (updateTask.isSuccessful()) {
                                                Log.d("LogisticsFragment", "Successfully added user note");
                                            } else {
                                                Log.d("LogisticsFragment", "Error updating user note: ", updateTask.getException());
                                            }
                                        });
                            }
                        } else {
                            Log.d("LogisticsFragment", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}