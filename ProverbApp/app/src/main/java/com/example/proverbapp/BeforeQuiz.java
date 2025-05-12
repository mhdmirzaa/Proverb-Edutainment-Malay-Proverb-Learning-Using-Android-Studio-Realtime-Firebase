package com.example.proverbapp;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BeforeQuiz extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_quiz);

        // Initialize Views
        TextView appTitle = findViewById(R.id.navTitle);
        ImageView starImage = findViewById(R.id.starImage);
        TextView perfectScorerTitle = findViewById(R.id.perfectScorerTitle);
        TextView perfectScorerDescription = findViewById(R.id.perfectScorerDescription);
        Button nextLevelButton = findViewById(R.id.nextLevelButton);

        // Set onClick listeners
        nextLevelButton.setOnClickListener(v -> {
            // Navigate to the next level
            Intent intent = new Intent(BeforeQuiz.this, QuizActivity.class);
            startActivity(intent);
            finish();
        });

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());
    }
}
