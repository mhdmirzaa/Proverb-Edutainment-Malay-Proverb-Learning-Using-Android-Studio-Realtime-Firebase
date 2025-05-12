package com.example.proverbapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ProverbDetailActivity extends AppCompatActivity {

    private Button anotherProverbsButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proverb_detail);

        ImageView backButton = findViewById(R.id.backButton);
        anotherProverbsButton = findViewById(R.id.anotherProverbsButton);

        // Back button functionality
        backButton.setOnClickListener(v -> finish());

        // Get data from the intent
        String proverb = getIntent().getStringExtra("proverb");
        String explanation = getIntent().getStringExtra("explanation");
        int currentProverb = getIntent().getIntExtra("currentProverb", 0);
        int totalProverbs = getIntent().getIntExtra("totalProverbs", 0);


        // Initialize TextViews
        TextView proverbTextView = findViewById(R.id.proverbTextView);
        TextView explanationTextView = findViewById(R.id.explanationTextView);
        TextView dueDateTextView = findViewById(R.id.dueDate);
        TextView progressTextView = findViewById(R.id.progress);

        // Set the proverb and explanation
        proverbTextView.setText(proverb);
        explanationTextView.setText(explanation);

        // Set the current date dynamically
        String currentDate = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                .format(Calendar.getInstance().getTime());
        dueDateTextView.setText(currentDate);

        // Set the progress dynamically
        if (totalProverbs > 0) {
            progressTextView.setText(currentProverb + "/" + totalProverbs + " Proverbs");
        } else {
            progressTextView.setText("No progress available");
        }

        // Handle continue learning button click
        anotherProverbsButton.setOnClickListener(v -> {
            // Add logic for "Continue Learning"
            finish();
        });
    }
}
