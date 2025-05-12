package com.example.proverbapp;

import static android.content.ContentValues.TAG;
import java.text.DecimalFormat;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.github.mikephil.charting.formatter.ValueFormatter;
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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    private TextView userNameTextView, quizProgressText;
    private ProgressBar quizProgressBar;
    private LinearLayout proverbQuizList;
    private FirebaseAuth auth;
    private DatabaseReference userRef, databaseReference;
    private PieChart pieChart;
    private RelativeLayout extraButton;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize views
        quizProgressBar = findViewById(R.id.progress_bar);
        quizProgressText = findViewById(R.id.quiz_progress_text);
        pieChart = findViewById(R.id.pieChart);
        proverbQuizList = findViewById(R.id.proverbQuizList);

        extraButton = findViewById(R.id.extraButton);
        ImageView homeMenu = findViewById(R.id.menu_home);
        ImageView chartButton = findViewById(R.id.menu_leaderboard);
        ImageView profileButton = findViewById(R.id.menu_profile);
        ImageView quitButton = findViewById(R.id.quitButton);
        RelativeLayout quizSection = findViewById(R.id.quizButton);
        RelativeLayout lessonSection = findViewById(R.id.LessonPart);
        userNameTextView = findViewById(R.id.userNameTextView);

        // Initialize Firebase Auth and Firestore
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();
        String userId = auth.getCurrentUser() != null ? auth.getCurrentUser().getUid() : "unknown_user";
        userRef = FirebaseDatabase.getInstance("https://proverb-5dcf7-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("users").child(userId);

        databaseReference = FirebaseDatabase.getInstance("https://proverb-5dcf7-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("users");

        // Load user name from Firestore or Realtime Database
        loadUserNameFromFirestore();

        // Fetch and quiz progress
        fetchQuizProgress();
        fetchQuizProgressRealTime(); // Keep progress bar logic intact

        // Quiz Section Click Action
        quizSection.setOnClickListener(v -> {
            Intent intent = new Intent(Home.this, BeforeQuiz.class); // Replace with your quiz activity
            startActivity(intent);
        });

        // Lesson Section Click Action
        lessonSection.setOnClickListener(v -> {
            Intent intent = new Intent(Home.this, CategoryListActivity.class); // Replace with your lesson activity
            startActivity(intent);
        });

        // Extra Button Click Action
        extraButton.setOnClickListener(v -> {
            Intent intent = new Intent(Home.this, ExtraQuizActivity.class);
            startActivity(intent);
        });

        // Bottom Navigation Actions
        homeMenu.setOnClickListener(v -> Toast.makeText(Home.this, "You are already on the Home screen.", Toast.LENGTH_SHORT).show());
        chartButton.setOnClickListener(v -> startActivity(new Intent(Home.this, LeaderboardActivity.class)));
        profileButton.setOnClickListener(v -> startActivity(new Intent(Home.this, profileActivity.class)));
        quitButton.setOnClickListener(v -> {
            new androidx.appcompat.app.AlertDialog.Builder(Home.this)
                    .setTitle("Logout Confirmation")
                    .setMessage("Are you sure you want to log out?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        auth.signOut();
                        Intent intent = new Intent(Home.this, login.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        Toast.makeText(Home.this, "You have been logged out.", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                    .show();
        });
    }

    private void loadUserNameFromFirestore() {
        String userId = auth.getCurrentUser() != null ? auth.getCurrentUser().getUid() : null;

        if (userId == null) {
            Toast.makeText(this, "User not authenticated!", Toast.LENGTH_SHORT).show();
            return;
        }

        firestore.collection("users").document(userId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String userName = documentSnapshot.getString("name");
                        userNameTextView.setText(userName != null ? "Hi " + userName : "Hi Anonymous");
                    } else {
                        Toast.makeText(this, "User not found in Firestore!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to fetch user name from Firestore.", Toast.LENGTH_SHORT).show());
    }

    private void fetchQuizProgressRealTime() {
        userRef.child("quizProgress").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long totalQuestions = 107; // Replace with the actual total questions across all categories
                long answeredQuestions = 0;

                for (DataSnapshot category : snapshot.getChildren()) {
                    Long score = category.child("score").getValue(Long.class);
                    if (score != null) {
                        answeredQuestions += score;
                    }
                }

                int progress = totalQuestions > 0 ? (int) ((answeredQuestions * 100) / totalQuestions) : 0;
                quizProgressBar.setProgress(progress);
                quizProgressText.setText("Progress: " + answeredQuestions + "/" + totalQuestions);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Home.this, "Failed to fetch quiz progress.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchQuizProgress() {
        userRef.child("quizProgress").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<PieEntry> entries = new ArrayList<>();
                List<Integer> colors = new ArrayList<>();

                proverbQuizList.removeAllViews(); // Clear existing list

                for (DataSnapshot categorySnapshot : snapshot.getChildren()) {
                    String categoryName = categorySnapshot.getKey(); // Category name
                    Long score = categorySnapshot.child("score").getValue(Long.class); // Score

                    if (score != null) {
                        // Add only the score to the PieEntry
                        entries.add(new PieEntry(score));
                        colors.add(generateRandomColor());

                        // Add category to the proverb list dynamically
                        addProverbQuizItem(categoryName, score, colors.get(colors.size() - 1));
                    }
                }

                if (entries.isEmpty()) {
                    Toast.makeText(Home.this, "No quiz progress data available!", Toast.LENGTH_SHORT).show();
                } else {
                    setupPieChart(entries, colors);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Home.this, "Failed to load quiz progress.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void setupPieChart(List<PieEntry> entries, List<Integer> colors) {
        PieDataSet dataSet = new PieDataSet(entries, "Categories");
        dataSet.setColors(colors);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setValueTextSize(14f);

        // Apply custom formatter using DecimalFormat
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new ValueFormatter() {
            private final DecimalFormat format = new DecimalFormat("###"); // No decimals

            @Override
            public String getFormattedValue(float value) {
                return format.format(value);
            }
        });

        pieChart.setData(data);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleRadius(58f);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setCenterText("Proverb Quiz");
        pieChart.setCenterTextColor(Color.DKGRAY);
        pieChart.setCenterTextSize(18f);
        pieChart.getDescription().setEnabled(false);

        // Disable the legend (category indicators below the chart)
        pieChart.getLegend().setEnabled(false);

        pieChart.animateY(1400);
        pieChart.invalidate();
    }


    private void addProverbQuizItem(String categoryName, long score, int color) {
        LinearLayout itemLayout = new LinearLayout(this);
        itemLayout.setOrientation(LinearLayout.HORIZONTAL);
        itemLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        itemLayout.setPadding(0, 8, 0, 8);

        View colorIndicator = new View(this);
        LinearLayout.LayoutParams colorParams = new LinearLayout.LayoutParams(40, 40);
        colorParams.setMargins(0, 10, 16, 0);
        colorIndicator.setLayoutParams(colorParams);
        colorIndicator.setBackgroundColor(color);

        TextView quizName = new TextView(this);
        quizName.setText(categoryName + " (Score: " + score + ")");
        quizName.setTextSize(14);
        quizName.setTextColor(Color.parseColor("#4A4A4A"));

        itemLayout.addView(colorIndicator);
        itemLayout.addView(quizName);

        proverbQuizList.addView(itemLayout);
    }

    private int generateRandomColor() {
        return Color.rgb((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256));
    }
}
