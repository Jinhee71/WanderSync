package com.example.sprintproject.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class LoginViewModel extends ViewModel {

    private FirebaseAuth mAuth;
    private MutableLiveData<FirebaseUser> userLiveData;
    private MutableLiveData<String> errorMessage;

    // Constructor
    public LoginViewModel() {
        mAuth = FirebaseAuth.getInstance();
        userLiveData = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
    }

    public LoginViewModel(FirebaseAuth auth) {
        this.mAuth = auth;
        userLiveData = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
    }

    // Returns FirebaseUser LiveData
    public LiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }

    // Returns error message LiveData
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    // Login method
    public void loginUser(String email, String password) {
        email += "@gmail.com";
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Login successful
                            FirebaseUser user = task.getResult().getUser();
                            userLiveData.setValue(user);  // Save user info in LiveData
                        } else {
                            // Login failed
                            errorMessage.setValue("Invalid email or password: ");
                        }
                    }
                });
    }

    // Method to validate email format
    public boolean isEmailValid(String email) {
        // Use a stricter regex to check for a valid email format
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

}
