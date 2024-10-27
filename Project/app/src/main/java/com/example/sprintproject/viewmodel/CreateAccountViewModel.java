package com.example.sprintproject.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sprintproject.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CreateAccountViewModel extends ViewModel {
    private FirebaseAuth mAuth;
    private MutableLiveData<Boolean> isAccountCreated = new MutableLiveData<>();
    private MutableLiveData<String> message = new MutableLiveData<>();

    private FirebaseFirestore db;

    public CreateAccountViewModel() {

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

    }

    public LiveData<Boolean> isAccountCreated() {

        return isAccountCreated;
    }

    public LiveData<String> getMessage() {

        return message;
    }


    private void createTripAndAddUser(FirebaseUser firebaseUser) {
        Map<String, Object> tripData = new HashMap<>();
        tripData.put("duration", 10);

        db.collection("Trip")
                .add(tripData)
                .addOnSuccessListener(tripDocumentReference -> {
                    String tripId = tripDocumentReference.getId();
                    Log.d("Firestore", "Trip created with ID: " + tripId);

                    addUserToFirestore(firebaseUser, tripId);
                })
                .addOnFailureListener(e -> Log.w("Firestore", "Error creating trip document", e));
    }

    private void addUserToFirestore(FirebaseUser firebaseUser, String tripId) {
        Map<String, Object> userData = new HashMap<>();
        userData.put("authUID", firebaseUser.getUid());
        userData.put("email", firebaseUser.getEmail());
        userData.put("activeTrip", tripId);
        userData.put("allocatedDays", 5); // default to 5

        db.collection("User")
                .add(userData)
                .addOnSuccessListener(userDocumentReference ->
                        Log.d("Firestore", "User created with ID: " + userDocumentReference.getId())
                )
                .addOnFailureListener(e ->
                        Log.w("Firestore", "Error adding user document", e)
                );
    }


    public void createUser(User user) {
        String email = user.getUsername() + "@gmail.com";
        String password = user.getPassword();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.w("AccountCreation", "Account successfully created");
                            isAccountCreated.setValue(true);
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            createTripAndAddUser(firebaseUser);
                        } else {
                            Log.e("AccountCreation", task.getException().getMessage());
                            message.setValue("Account already exists: "
                                    + task.getException().getMessage());
                            isAccountCreated.setValue(false);
                        }
                    }
                });
//        FirebaseUser firebaseUser = mAuth.getCurrentUser();
//        ArrayList<String> usersList = new ArrayList<>();
//
//        Map<String, Object> tripData = new HashMap<>();
//        tripData.put("duration", 10);
//
//        ArrayList<String> tripID = new ArrayList<>();
//
//        db.collection("Trip")
//                .add(tripData)
//                .addOnSuccessListener(documentReference -> {
//                    // Success
//                    Log.d("Firestore", "DocumentSnapshot added with ID: " + documentReference.getId());
//                    tripID.add(documentReference.getId());
//                })
//                .addOnFailureListener(e -> {
//                    // Failure
//                    Log.w("Firestore", "Error adding document", e);
//                });
//
//        Map<String, Object> userData = new HashMap<>();
//        userData.put("authUID", firebaseUser.getUid());
//        userData.put("email", firebaseUser.getEmail());
//        userData.put("activeTrip", tripID.get(0));
//        userData.put("allocatedDays", 5);
//
//        db.collection("User")
//                .add(userData)
//                .addOnSuccessListener(documentReference -> {
//                    // Success
//                    Log.d("Firestore", "DocumentSnapshot added with ID: " + documentReference.getId());
//                })
//                .addOnFailureListener(e -> {
//                    // Failure
//                    Log.w("Firestore", "Error adding document", e);
//                });
//
//        message.setValue("Account successfully created.");
//    }
    }
}

