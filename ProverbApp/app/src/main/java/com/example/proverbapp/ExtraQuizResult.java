package com.example.proverbapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ExtraQuizResult extends AppCompatActivity {

    private TextView scoreTextView;
    private Button finishButton, retryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra_quiz_result);

        scoreTextView = findViewById(R.id.scoreTextView);
        finishButton = findViewById(R.id.finishButton);
        retryButton = findViewById(R.id.retryButton);

        int score = getIntent().getIntExtra("SCORE", 0);
        int totalQuestions = getIntent().getIntExtra("TOTAL_QUESTIONS", 20);

        scoreTextView.setText("You scored " + score + " out of " + totalQuestions);

        finishButton.setOnClickListener(v -> {
            Intent intent = new Intent(ExtraQuizResult.this, Home.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });

        retryButton.setOnClickListener(v -> {
            Intent intent = new Intent(ExtraQuizResult.this, ExtraQuizActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
