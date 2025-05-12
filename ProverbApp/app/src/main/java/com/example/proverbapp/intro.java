package com.example.proverbapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class intro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        // Reference the "Get started" button
        Button getStartedButton = findViewById(R.id.getStartedButton);

        // Set click listener for "Get started" button
        getStartedButton.setOnClickListener(view -> {
            // Navigate to the login page
            Intent intent = new Intent(intro.this, login.class);
            startActivity(intent);
        });
    }
}
