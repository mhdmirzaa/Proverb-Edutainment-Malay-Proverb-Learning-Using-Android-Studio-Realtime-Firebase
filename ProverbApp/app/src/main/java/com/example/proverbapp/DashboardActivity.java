package com.example.proverbapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private static final String TAG = "DashboardActivity";
    private PieChart pieChart;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        pieChart = findViewById(R.id.pieChart);
        Button lessonButton = findViewById(R.id.lessonButton);
        Button quizButton = findViewById(R.id.quizButton);
        Button chartButton = findViewById(R.id.chartButton);
        Button profileButton = findViewById(R.id.profileButton);
        Button quitButton = findViewById(R.id.quitButton);

        auth = FirebaseAuth.getInstance();

        // Get a reference to the user's quiz progress in Realtime Database
        String userId = auth.getCurrentUser() != null ? auth.getCurrentUser().getUid() : null;
        if (userId == null) {
            Toast.makeText(this, "User not authenticated!", Toast.LENGTH_SHORT).show();
            return;
        }

        databaseReference = FirebaseDatabase.getInstance("https://proverb-5dcf7-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("users")
                .child(userId)
                .child("quizProgress");

        // Initialize pie chart with empty data
        setupPieChart(new ArrayList<>());

        // Listen for real-time updates
        fetchQuizProgress();

        // Lesson button action
        lessonButton.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, CategoryListActivity.class);
            startActivity(intent);
        });

        // Quiz button action
        quizButton.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, QuizActivity.class);
            startActivity(intent);
        });

        chartButton.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, LeaderboardActivity.class);
            startActivity(intent);
        });

        profileButton.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, profileActivity.class);
            startActivity(intent);
        });

        // Quit button action
        quitButton.setOnClickListener(v -> finishAffinity());
    }

    private void fetchQuizProgress() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<PieEntry> entries = new ArrayList<>();
                Log.d(TAG, "DataSnapshot: " + snapshot.toString()); // Log snapshot for debugging

                // Iterate through each child in "quizProgress"
                for (DataSnapshot categorySnapshot : snapshot.getChildren()) {
                    String categoryName = categorySnapshot.getKey(); // The category name (e.g., "Akibat")

                    // Ensure the score field exists
                    if (categorySnapshot.child("score").exists()) {
                        try {
                            long score = categorySnapshot.child("score").getValue(Long.class); // Parse the score
                            Log.d(TAG, "Category: " + categoryName + ", Score: " + score); // Debug each category and score
                            entries.add(new PieEntry(score, categoryName)); // Add entry to PieChart
                        } catch (Exception e) {
                            Log.e(TAG, "Error parsing score for category: " + categoryName, e);
                        }
                    } else {
                        Log.d(TAG, "Category: " + categoryName + " has no 'score' field.");
                    }
                }

                if (entries.isEmpty()) {
                    Log.d(TAG, "No quiz progress data found."); // Debug if no data
                    Toast.makeText(DashboardActivity.this, "No quiz progress data available!", Toast.LENGTH_SHORT).show();
                } else {
                    setupPieChart(entries); // Update PieChart with data
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Database Error: " + error.getMessage());
                Toast.makeText(DashboardActivity.this, "Failed to load quiz progress.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupPieChart(List<PieEntry> entries) {
        PieDataSet dataSet = new PieDataSet(entries, "Categories");
        dataSet.setColors(new int[]{
                getColor(android.R.color.holo_blue_light),
                getColor(android.R.color.holo_green_light),
                getColor(android.R.color.holo_orange_light),
                getColor(android.R.color.holo_red_light),
                getColor(android.R.color.holo_purple)
        });
        dataSet.setValueTextSize(14f);

        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Quiz Progress");
        pieChart.setCenterTextSize(16f);
        pieChart.setUsePercentValues(true); // Show values as percentages
        pieChart.animateY(1000);
        pieChart.invalidate(); // Refresh the chart
    }
}
