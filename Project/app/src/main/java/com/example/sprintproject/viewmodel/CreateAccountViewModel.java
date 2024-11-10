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

    public void setmAuth(FirebaseAuth mAuth) {
        this.mAuth = mAuth;
    }

    public void setMessage(MutableLiveData<String> message) {
        this.message = message;
    }

    public void setIsAccountCreated(MutableLiveData<Boolean> isAccountCreated) {
        this.isAccountCreated = isAccountCreated;
    }

    private void createTripAndAddUser(FirebaseUser firebaseUser) {
        Map<String, Object> tripData = new HashMap<>();
        tripData.put("duration", 365); //Defaults time to 365 (for now)
        ArrayList<String> arrList = new ArrayList<String>();
        arrList.add(firebaseUser.getUid());
        tripData.put("User IDs", arrList);

        ArrayList<String> accommodationsList = new ArrayList<>();
        tripData.put("accommodations", accommodationsList);

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
        userData.put("allocatedDays", 365); // default to 356

        ArrayList<String> arrList = new ArrayList<String>();
        userData.put("notes", arrList);

        db.collection("User").document(firebaseUser.getUid())
                .set(userData)
                .addOnSuccessListener(userDocumentReference ->
                        Log.d("Firestore", "User created with ID: " + firebaseUser.getUid())
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
    }
}

