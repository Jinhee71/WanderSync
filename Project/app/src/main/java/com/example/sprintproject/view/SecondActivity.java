package com.example.sprintproject.view;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


import com.example.sprintproject.R;


public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(SecondActivity.this, FragmentHandlerActivity.class);
        startActivity(intent);
//        setContentView(R.layout.activity_main);
//        setContentView(R.layout.loginscreen);
//
//        Button createButton = findViewById(R.id.create_button);
//
//        // Navigate to a different activity on Create an Account Button click
//        createButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(SecondActivity.this, AccountCreationActivity.class);
//                startActivity(intent);
//            }
//        });
    }
}
