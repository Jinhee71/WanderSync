package com.example.sprintproject.view;

import com.example.sprintproject.viewmodel.LoginViewModel;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.google.firebase.auth.FirebaseUser;
import com.example.sprintproject.R;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private Button loginButton, createAccountButton, quitButton;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginscreen); // Connect the predefined XML layout file

        // Initialize ViewModel
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        // Connect views
        usernameEditText = findViewById(R.id.usernameEditText); // Username field
        passwordEditText = findViewById(R.id.passwordEditText); // Password field
        loginButton = findViewById(R.id.loginButton);           // Login button
        createAccountButton = findViewById(R.id.createAccountButton); // Create an Account button
        quitButton = findViewById(R.id.quitButton);             // Quit button

        // Handle login button click event
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        // Handle create account button click event (can be extended)
        // please change SecondActivity.class to AccountCreationActivity.class
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, AccountCreationActivity.class);
                startActivity(intent);
            }
        });

        // Handle quit button click event
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity(); // Close the app
            }
        });



        // Observe login status through ViewModel
        loginViewModel.getUserLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null) {
                    // Login successful, show a message and navigate to SecondActivity
                    // SecondActivity.class should be changed to navbarscreen
                    Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, SecondActivity.class);
                    startActivity(intent);
                    finish(); // Optionally finish the current activity so it's removed from the back stack
                }
            }
        });

        // Observe error messages through ViewModel
        loginViewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String error) {
                if (!TextUtils.isEmpty(error)) {
                    Toast.makeText(LoginActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loginUser() {
        String email = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (TextUtils.isEmpty(email)) {
            usernameEditText.setError("Email is required.");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Password is required.");
            return;
        }

        // Process login through ViewModel
        loginViewModel.loginUser(email, password);
    }
}
