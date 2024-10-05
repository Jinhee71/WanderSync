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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AccountCreationActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_creation);

        Button registerButton = findViewById(R.id.register_button);
        Button loginButton = findViewById(R.id.login_button);

        mAuth = FirebaseAuth.getInstance();

        EditText usernameEditText = findViewById(R.id.username_edittext);
        EditText passwordEditText = findViewById(R.id.password_edittext);

        // Register Button click
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usernameEditText.getText().toString().trim();
                password = passwordEditText.getText().toString().trim();
                if (username.isEmpty() || username.contains(" ")) {
                    Toast.makeText(AccountCreationActivity.this, "Please enter a valid username.", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty() || password.contains(" ")) {
                    Toast.makeText(AccountCreationActivity.this, "Please enter a valid password.", Toast.LENGTH_SHORT).show();
                } else {
                    username = username + "@gmail.com";
                    mAuth.createUserWithEmailAndPassword(username, password)
                            .addOnCompleteListener(AccountCreationActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("success", "createUserWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("success", "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(AccountCreationActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        // Navigate to a different activity on Login Button click
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountCreationActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
    }
}