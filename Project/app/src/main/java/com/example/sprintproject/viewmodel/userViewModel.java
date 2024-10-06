package com.example.sprintproject.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.sprintproject.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class userViewModel {
    private FirebaseAuth mAuth;
    private MutableLiveData<FirebaseUser> userLiveData;
    private MutableLiveData<String> errorMessage;

    public userViewModel() {
        mAuth = FirebaseAuth.getInstance();
        userLiveData = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
    }


    public MutableLiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void register(User user) {
        if (validateUser(user)) {
            mAuth.createUserWithEmailAndPassword(user.getUsername(), user.getPassword())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d("success", "createUserWithEmail:success");
                                userLiveData.postValue(mAuth.getCurrentUser());
                            } else {
                                Log.w("failure", "createUserWithEmail:failure", task.getException());
                                errorMessage.postValue("Account already exists or error occurred.");
                            }
                        }
                    });
        }
    }

    private boolean validateUser(User user) {
        if (user.getUsername().isEmpty() || user.getUsername().contains(" ")) {
            errorMessage.postValue("Please enter a valid username");
            return false;
        } else if (user.getPassword().isEmpty() || user.getPassword().contains(" ")) {
            errorMessage.postValue("Please enter a valid password");
            return false;
        }
        user.setUsername(user.getUsername() + "@gmail.com"); // Email suffix logic
        return true;
    }



}
