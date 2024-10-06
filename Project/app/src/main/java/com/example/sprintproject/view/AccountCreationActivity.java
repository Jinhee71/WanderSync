package com.example.sprintproject.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.sprintproject.R;
import com.example.sprintproject.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.example.sprintproject.viewmodel.userViewModel;

public class AccountCreationActivity extends AppCompatActivity {

    private userViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_creation);

        Button registerButton = findViewById(R.id.register_button);
        Button loginButton = findViewById(R.id.login_button);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        EditText usernameEditText = findViewById(R.id.username_edittext);
        EditText passwordEditText = findViewById(R.id.password_edittext);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                user.setUsername(usernameEditText.getText().toString().trim());
                user.setPassword(passwordEditText.getText().toString().trim());
                userViewModel.register(user);
            }
        });

        /*
        // Navigate to a different activity on Login Button click
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountCreationActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
    }

         */
    }
}