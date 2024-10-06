package com.example.sprintproject.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sprintproject.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class CreateAccountViewModel extends ViewModel {
    private FirebaseAuth mAuth;
    private MutableLiveData<Boolean> isAccountCreated = new MutableLiveData<>();
    private MutableLiveData<String> message = new MutableLiveData<>();

    public CreateAccountViewModel() {

        mAuth = FirebaseAuth.getInstance();
    }

    public LiveData<Boolean> isAccountCreated() {

        return isAccountCreated;
    }

    public LiveData<String> getMessage() {

        return message;
    }

    public void createUser(User user) {
        String email = user.getUsername() + "@gmail.com";
        String password = user.getPassword();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            isAccountCreated.setValue(true);
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            message.setValue("Account successfully created.");
                        } else {
                            isAccountCreated.setValue(false);
                            message.setValue("Account already exists: " + task.getException().getMessage());
                        }
                    }
                });
    }
}
