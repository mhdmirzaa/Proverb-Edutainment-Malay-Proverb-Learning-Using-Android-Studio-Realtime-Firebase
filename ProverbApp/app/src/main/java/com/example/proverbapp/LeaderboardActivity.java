package com.example.proverbapp;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LeaderboardActivity extends AppCompatActivity {

    private static final String TAG = "LeaderboardActivity";

    private RecyclerView leaderboardRecyclerView;
    private List<UserScore> userScores;
    private LeaderboardAdapter leaderboardAdapter;

    private ImageView firstPlaceImageView, secondPlaceImageView, thirdPlaceImageView;
    private TextView firstPlaceNameTextView, secondPlaceNameTextView, thirdPlaceNameTextView;
    private TextView firstPlaceScoreTextView, secondPlaceScoreTextView, thirdPlaceScoreTextView;

    private FirebaseFirestore firestore;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        // Back Button Listener
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(view -> finish()); // Closes the current activity

        // Initialize views
        ImageView homeMenu = findViewById(R.id.menu_home);
        ImageView chartButton = findViewById(R.id.menu_leaderboard);
        ImageView profileButton = findViewById(R.id.menu_profile);

        // Bottom Navigation Actions
        homeMenu.setOnClickListener(v -> {
            Intent intent = new Intent(LeaderboardActivity.this, Home.class);
            startActivity(intent);
        });

        chartButton.setOnClickListener(v -> {
            Intent intent = new Intent(LeaderboardActivity.this, LeaderboardActivity.class);
            Toast.makeText(LeaderboardActivity.this, "You are already on the Leaderboard page.", Toast.LENGTH_SHORT).show();
        });

        profileButton.setOnClickListener(v -> {
            Intent intent = new Intent(LeaderboardActivity.this, profileActivity.class);
            startActivity(intent);
        });

        try {
            firestore = FirebaseFirestore.getInstance();
            databaseReference = FirebaseDatabase.getInstance("https://proverb-5dcf7-default-rtdb.asia-southeast1.firebasedatabase.app/")
                    .getReference("users");

            // Top 3 UI elements
            firstPlaceImageView = findViewById(R.id.firstPlaceImageView);
            secondPlaceImageView = findViewById(R.id.secondPlaceImageView);
            thirdPlaceImageView = findViewById(R.id.thirdPlaceImageView);

            firstPlaceNameTextView = findViewById(R.id.firstPlaceNameTextView);
            secondPlaceNameTextView = findViewById(R.id.secondPlaceNameTextView);
            thirdPlaceNameTextView = findViewById(R.id.thirdPlaceNameTextView);

            firstPlaceScoreTextView = findViewById(R.id.firstPlaceScoreTextView);
            secondPlaceScoreTextView = findViewById(R.id.secondPlaceScoreTextView);
            thirdPlaceScoreTextView = findViewById(R.id.thirdPlaceScoreTextView);

            leaderboardRecyclerView = findViewById(R.id.leaderboardRecyclerView);
            leaderboardRecyclerView.setLayoutManager(new LinearLayoutManager(this));

            // Add spacing between items in the RecyclerView
            leaderboardRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                    int position = parent.getChildAdapterPosition(view);
                    if (position != RecyclerView.NO_POSITION) {
                        outRect.top = 16;  // Top spacing
                        outRect.bottom = 16; // Bottom spacing
                    }
                }
            });

            userScores = new ArrayList<>();
            leaderboardAdapter = new LeaderboardAdapter(userScores);
            leaderboardRecyclerView.setAdapter(leaderboardAdapter);

            fetchLeaderboardData();
        } catch (Exception e) {
            Log.e(TAG, "Error in onCreate: ", e);
            Toast.makeText(this, "An error occurred while loading the leaderboard.", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchLeaderboardData() {
        databaseReference.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userScores.clear();

                if (!snapshot.exists()) {
                    Log.w(TAG, "No data found in Realtime Database.");
                    Toast.makeText(LeaderboardActivity.this, "No leaderboard data available.", Toast.LENGTH_SHORT).show();
                    return;
                }

                int totalUsers = (int) snapshot.getChildrenCount();
                int[] processedUsers = {0}; // Track processed users

                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    String userId = userSnapshot.getKey();
                    String profileImageUrl = userSnapshot.child("profileImage").getValue(String.class);

                    DataSnapshot extraQuizSnapshot = userSnapshot.child("extraQuiz");
                    Long finalScore = extraQuizSnapshot.child("finalScore").getValue(Long.class);

                    if (finalScore != null && finalScore > 0) {
                        fetchNameFromFirestore(userId, profileImageUrl, finalScore, totalUsers, processedUsers);
                    } else {
                        Log.w(TAG, "User with ID " + userId + " has no valid final score.");
                        processedUsers[0]++;
                        if (processedUsers[0] == totalUsers) {
                            updateLeaderboardUI();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Error fetching leaderboard data: ", error.toException());
                Toast.makeText(LeaderboardActivity.this, "Error fetching leaderboard data.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void fetchNameFromFirestore(String userId, String profileImageUrl, long finalScore, int totalUsers, int[] processedUsers) {
        firestore.collection("users").document(userId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String userName = documentSnapshot.getString("name");
                        if (userName == null || userName.isEmpty()) userName = "Anonymous";

                        userScores.add(new UserScore(userId, userName, profileImageUrl, finalScore));
                    } else {
                        Log.w(TAG, "No Firestore data for user ID: " + userId);
                    }

                    processedUsers[0]++;
                    if (processedUsers[0] == totalUsers) {
                        updateLeaderboardUI();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error fetching Firestore data for user ID: " + userId, e);
                    processedUsers[0]++;
                    if (processedUsers[0] == totalUsers) {
                        updateLeaderboardUI();
                    }
                });
    }


    private long calculateTotalScore(DataSnapshot quizProgressSnapshot) {
        long totalScore = 0;

        for (DataSnapshot categorySnapshot : quizProgressSnapshot.getChildren()) {
            Long score = categorySnapshot.child("score").getValue(Long.class);
            if (score != null) {
                totalScore += score;
            }
        }

        return totalScore;
    }

    private void updateLeaderboardUI() {
        try {
            // Sort the scores in descending order
            Collections.sort(userScores, Comparator.comparingLong(UserScore::getTotalScore).reversed());

            // Update top 3
            if (userScores.size() > 0) {
                setTopUser(userScores.get(0), firstPlaceNameTextView, firstPlaceScoreTextView, firstPlaceImageView);
            } else {
                clearTopUser(firstPlaceNameTextView, firstPlaceScoreTextView, firstPlaceImageView);
            }

            if (userScores.size() > 1) {
                setTopUser(userScores.get(1), secondPlaceNameTextView, secondPlaceScoreTextView, secondPlaceImageView);
            } else {
                clearTopUser(secondPlaceNameTextView, secondPlaceScoreTextView, secondPlaceImageView);
            }

            if (userScores.size() > 2) {
                setTopUser(userScores.get(2), thirdPlaceNameTextView, thirdPlaceScoreTextView, thirdPlaceImageView);
            } else {
                clearTopUser(thirdPlaceNameTextView, thirdPlaceScoreTextView, thirdPlaceImageView);
            }

            // Update RecyclerView for users beyond the top 3
            if (userScores.size() > 3) {
                List<UserScore> rest = new ArrayList<>(userScores.subList(3, userScores.size()));
                Log.d(TAG, "Remaining Users: " + rest.size());
                leaderboardAdapter.updateData(rest); // Pass the remaining users to the adapter
            } else {
                leaderboardAdapter.updateData(new ArrayList<>()); // Clear adapter if no extra users
            }
        } catch (Exception e) {
            Log.e(TAG, "Error updating leaderboard UI: ", e);
        }
    }

    private void setTopUser(UserScore user, TextView nameView, TextView scoreView, ImageView imageView) {
        nameView.setText(user.getUserName());
        scoreView.setText(String.valueOf(user.getTotalScore()));

        if (user.getProfileImageUrl() != null && !user.getProfileImageUrl().isEmpty()) {
            Glide.with(this)
                    .load(Base64.decode(user.getProfileImageUrl(), Base64.DEFAULT))
                    .circleCrop() // Apply circle crop transformation
                    .placeholder(R.drawable.profile) // Placeholder image
                    .into(imageView);
        } else {
            imageView.setImageResource(R.drawable.profile); // Default placeholder
        }
    }

    private void clearTopUser(TextView nameView, TextView scoreView, ImageView imageView) {
        nameView.setText("Name");
        scoreView.setText("0");
        imageView.setImageResource(R.drawable.profile);
    }
}
