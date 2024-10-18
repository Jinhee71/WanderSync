package com.example.sprintproject.view;
import com.example.sprintproject.R;
import com.example.sprintproject.model.User;
import com.example.sprintproject.viewmodel.CreateAccountViewModel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class AccountCreationActivity extends AppCompatActivity {

    private CreateAccountViewModel createAccountViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_creation);

        EditText usernameEditText = findViewById(R.id.username_edittext);
        EditText passwordEditText = findViewById(R.id.password_edittext);
        Button registerButton = findViewById(R.id.register_button);
        Button loginButton = findViewById(R.id.login_button);

        createAccountViewModel = new ViewModelProvider(this).get(CreateAccountViewModel.class);

        // Observe the LiveData from the ViewModel
        createAccountViewModel.isAccountCreated().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isCreated) {
                if (isCreated) {
                    Toast.makeText(AccountCreationActivity.this,
                            "Account successfully created", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AccountCreationActivity.this,
                            createAccountViewModel.getMessage().getValue(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (username.isEmpty() || username.contains(" ")) {
                    Toast.makeText(AccountCreationActivity.this,
                            "Please enter a valid username", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty() || password.contains(" ")) {
                    Toast.makeText(AccountCreationActivity.this,
                            "Please enter a valid password", Toast.LENGTH_SHORT).show();
                } else if (password.length() <= 6) {
                    Toast.makeText(AccountCreationActivity.this,
                            "Please enter a password with at least 7 characters",
                            Toast.LENGTH_SHORT).show();
                } else {
                    User user = new User(username, password, null, null, 0);
                    createAccountViewModel.createUser(user);

                    Intent intent = new Intent(AccountCreationActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountCreationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


    }
}
