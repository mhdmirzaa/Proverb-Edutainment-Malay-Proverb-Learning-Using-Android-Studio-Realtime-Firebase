package com.example.proverbapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class QuizActivity extends AppCompatActivity {

    private TextView questionTextView;
    private String correctAnswer;
    private String currentProverbKey;
    private String currentCategoryKey;
    private SharedPreferences sharedPreferences;
    private Set<String> answeredKeys;
    private DatabaseReference databaseReference;
    private String userId;
    private Button option1, option2, option3, option4, quitButton;
    private RadioGroup optionsGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        ImageView backButton = findViewById(R.id.backButton);
        ImageView helpButton = findViewById(R.id.helpButton); // Add reference to helpButton
        questionTextView = findViewById(R.id.questionTextView);
        optionsGroup = findViewById(R.id.optionsGroup);

        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        quitButton = findViewById(R.id.quitButton);

        sharedPreferences = getSharedPreferences("QuizProgress", MODE_PRIVATE);
        answeredKeys = new HashSet<>(sharedPreferences.getStringSet("answeredKeys", new HashSet<>()));

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {
            userId = user.getUid();
        } else {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        databaseReference = FirebaseDatabase.getInstance("https://proverb-5dcf7-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();

        loadNewQuestion();

        option1.setOnClickListener(v -> checkAnswer(option1.getText().toString()));
        option2.setOnClickListener(v -> checkAnswer(option2.getText().toString()));
        option3.setOnClickListener(v -> checkAnswer(option3.getText().toString()));
        option4.setOnClickListener(v -> checkAnswer(option4.getText().toString()));

        backButton.setOnClickListener(v -> showBackConfirmationDialog());

        // Add functionality for Quit Button
        quitButton.setOnClickListener(v -> showQuitConfirmationDialog());

        // Add functionality for Help Button
        helpButton.setOnClickListener(v -> showHelpPopup());
    }

    private void showHelpPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Help");
        builder.setMessage("Just choose the answer below.");
        builder.setCancelable(true);

        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    private void showQuitConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Quit Quiz");
        builder.setMessage("Are you sure you want to quit the quiz?");
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", (dialog, which) -> {
            dialog.dismiss();
            finish(); // Close the quiz activity
        });

        builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showBackConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Exit Quiz");
        builder.setMessage("Are you sure you want to stop the quiz?");
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", (dialog, which) -> {
            dialog.dismiss();
            finish();
        });

        builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    private void loadNewQuestion() {
        Map<String, List<ProverbData.ProverbEntry>> categorizedProverbs = ProverbData.getProverbsByCategoryWithKeys();
        List<ProverbData.ProverbEntry> allProverbs = new ArrayList<>();
        categorizedProverbs.values().forEach(allProverbs::addAll);

        // Filter out answered proverbs
        List<ProverbData.ProverbEntry> remainingProverbs = new ArrayList<>();
        for (ProverbData.ProverbEntry entry : allProverbs) {
            if (!answeredKeys.contains(entry.getKey())) {
                remainingProverbs.add(entry);
            }
        }

        // Reset answered keys if all questions have been answered
        if (remainingProverbs.isEmpty()) {
            answeredKeys.clear();
            remainingProverbs.addAll(allProverbs);
        }

        // Randomly select a question
        Random random = new Random();
        ProverbData.ProverbEntry selectedEntry = remainingProverbs.get(random.nextInt(remainingProverbs.size()));
        correctAnswer = selectedEntry.getExplanation();
        currentProverbKey = selectedEntry.getKey();
        currentCategoryKey = getCategoryForProverb(currentProverbKey, categorizedProverbs); // Find the category key
        answeredKeys.add(currentProverbKey);

        questionTextView.setText(selectedEntry.getProverb());

        // Generate answer options
        List<String> options = new ArrayList<>();
        options.add(correctAnswer);
        while (options.size() < 4) {
            ProverbData.ProverbEntry randomEntry = allProverbs.get(random.nextInt(allProverbs.size()));
            String randomExplanation = randomEntry.getExplanation();
            if (!options.contains(randomExplanation)) {
                options.add(randomExplanation);
            }
        }

        // Shuffle the options
        Collections.shuffle(options);

        option1.setText(options.get(0));
        option2.setText(options.get(1));
        option3.setText(options.get(2));
        option4.setText(options.get(3));

        // Save answered keys to SharedPreferences
        sharedPreferences.edit().putStringSet("answeredKeys", answeredKeys).apply();
    }


    private void checkAnswer(String selectedAnswer) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false); // Prevent dialog from closing if clicked outside

        if (selectedAnswer.equals(correctAnswer)) {
            // Correct answer
            builder.setTitle("Correct!");
            builder.setMessage("Great job! You answered correctly.");
            builder.setPositiveButton("Next", (dialog, which) -> {
                dialog.dismiss();
                saveAnsweredProverbAndScore();
                loadNewQuestion();
            });
        } else {
            // Incorrect answer
            builder.setTitle("Incorrect!");
            builder.setMessage("Oops! Try again or review the explanation.");
            builder.setPositiveButton("Try Again", (dialog, which) -> dialog.dismiss());
        }

        // Show the dialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void saveAnsweredProverbAndScore() {
        DatabaseReference userRef = databaseReference.child("users").child(userId);

        // Reference for storing answered proverbs
        DatabaseReference answeredProverbsRef = userRef.child("answeredProverbs");

        // Reference for quiz progress in the category
        DatabaseReference categoryRef = userRef.child("quizProgress").child(currentCategoryKey);

        categoryRef.get().addOnSuccessListener(dataSnapshot -> {
            long currentScore = dataSnapshot.child("score").exists() ? dataSnapshot.child("score").getValue(Long.class) : 0;

            // Increment the score and add the timestamp
            Map<String, Object> updatedData = new HashMap<>();
            updatedData.put("score", currentScore + 1);
            updatedData.put("timestamp", System.currentTimeMillis());

            categoryRef.setValue(updatedData)
                    .addOnSuccessListener(aVoid -> {
                        // Save answered proverb as true
                        answeredProverbsRef.child(currentProverbKey).setValue(true)
                                .addOnSuccessListener(aVoid1 -> Toast.makeText(this, "Progress saved!", Toast.LENGTH_SHORT).show())
                                .addOnFailureListener(e -> Toast.makeText(this, "Failed to save answered proverb.", Toast.LENGTH_SHORT).show());
                    })
                    .addOnFailureListener(e -> Toast.makeText(this, "Failed to save progress.", Toast.LENGTH_SHORT).show());
        }).addOnFailureListener(e -> Toast.makeText(this, "Failed to retrieve current data.", Toast.LENGTH_SHORT).show());
    }

    private String getCategoryForProverb(String proverbKey, Map<String, List<ProverbData.ProverbEntry>> categorizedProverbs) {
        for (Map.Entry<String, List<ProverbData.ProverbEntry>> category : categorizedProverbs.entrySet()) {
            for (ProverbData.ProverbEntry entry : category.getValue()) {
                if (entry.getKey().equals(proverbKey)) {
                    return category.getKey();
                }
            }
        }
        return "Unknown"; // Default if category not found
    }
}
