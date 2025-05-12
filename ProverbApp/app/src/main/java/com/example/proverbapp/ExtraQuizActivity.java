package com.example.proverbapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class ExtraQuizActivity extends AppCompatActivity {

    private TextView questionTextView, timerTextView, questionCounterTextView;
    private RadioButton option1, option2, option3, option4;
    private RadioGroup optionsGroup;

    private List<ProverbData.ProverbEntry> allProverbs;
    private ProverbData.ProverbEntry currentQuestion;
    private int questionCounter = 0;
    private static final int TOTAL_QUESTIONS = 20;
    private int score = 0;

    private CountDownTimer countDownTimer;
    private static final long COUNTDOWN_IN_MILLIS = 30000; // 30 seconds
    private long timeLeftInMillis;

    private DatabaseReference databaseReference;
    private String userId;

    private boolean isPopupDisplayed = false; // Flag to prevent multiple popups

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra_quiz);

        // Initialize Firebase
        FirebaseAuth auth = FirebaseAuth.getInstance();
        userId = auth.getCurrentUser() != null ? auth.getCurrentUser().getUid() : null;
        databaseReference = FirebaseDatabase.getInstance("https://proverb-5dcf7-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference();

        // Initialize views
        questionTextView = findViewById(R.id.questionTextView);
        timerTextView = findViewById(R.id.timerTextView);
        questionCounterTextView = findViewById(R.id.questionCounterTextView);
        optionsGroup = findViewById(R.id.optionsGroup);
        ImageView backButton = findViewById(R.id.backButton);

        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);

        loadAllProverbs();

        backButton.setOnClickListener(v -> showBackConfirmationDialog());

        // Load the first question
        showNextQuestion();

        optionsGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId != -1 && !isPopupDisplayed) { // Prevent multiple popups
                isPopupDisplayed = true;
                checkAnswer();
            }
        });
    }

    private void showBackConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Exit Quiz");
        builder.setMessage("Are you sure you want to stop the quiz?");
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", (dialog, which) -> {
            dialog.dismiss();
            finish(); // Close the quiz activity
        });

        builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void loadAllProverbs() {
        // Load all proverbs from ProverbData and shuffle them
        allProverbs = new ArrayList<>();
        ProverbData.getProverbsByCategoryWithKeys().values().forEach(allProverbs::addAll);
        Collections.shuffle(allProverbs);
    }

    private void showNextQuestion() {
        if (questionCounter < TOTAL_QUESTIONS) {
            resetTimer();

            currentQuestion = allProverbs.get(questionCounter);

            questionTextView.setText(currentQuestion.getProverb());
            List<String> options = new ArrayList<>();
            options.add(currentQuestion.getExplanation());

            // Add 3 random explanations
            Random random = new Random();
            while (options.size() < 4) {
                ProverbData.ProverbEntry randomEntry = allProverbs.get(random.nextInt(allProverbs.size()));
                String randomExplanation = randomEntry.getExplanation();
                if (!options.contains(randomExplanation)) {
                    options.add(randomExplanation);
                }
            }

            // Shuffle options and set to radio buttons
            Collections.shuffle(options);
            option1.setText(options.get(0));
            option2.setText(options.get(1));
            option3.setText(options.get(2));
            option4.setText(options.get(3));

            questionCounter++;
            questionCounterTextView.setText("Question: " + questionCounter + "/" + TOTAL_QUESTIONS);

            startTimer();
        } else {
            finishQuiz();
        }
    }

    private void startTimer() {
        timeLeftInMillis = COUNTDOWN_IN_MILLIS;
        updateCountDownText();

        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                updateCountDownText();
                if (!isPopupDisplayed) {
                    isPopupDisplayed = true;
                    showTimeUpDialog();
                }
            }
        }.start();
    }

    private void resetTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        optionsGroup.clearCheck();
        isPopupDisplayed = false; // Reset popup flag
    }

    private void updateCountDownText() {
        int seconds = (int) (timeLeftInMillis / 1000);
        timerTextView.setText(String.format(Locale.getDefault(), "%02d sec", seconds));

        if (timeLeftInMillis < 10000) {
            timerTextView.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        } else {
            timerTextView.setTextColor(getResources().getColor(android.R.color.black));
        }
    }

    private void checkAnswer() {
        countDownTimer.cancel();
        RadioButton selectedOption = findViewById(optionsGroup.getCheckedRadioButtonId());
        String selectedAnswer = selectedOption.getText().toString();

        boolean isCorrect = selectedAnswer.equals(currentQuestion.getExplanation());
        if (isCorrect) {
            score++;
        }

        showAnswerPopup(isCorrect);
    }

    private void showAnswerPopup(boolean isCorrect) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);

        if (isCorrect) {
            builder.setTitle("Correct!");
            builder.setMessage("Good job!");
        } else {
            builder.setTitle("Incorrect!");
            builder.setMessage("The correct answer is: " + currentQuestion.getExplanation());
        }

        builder.setPositiveButton("Next", (dialog, which) -> {
            dialog.dismiss();
            showNextQuestion();
        });

        builder.show();
    }

    private void showTimeUpDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Time's Up!");
        builder.setMessage("You didn't answer in time. The correct answer is: " + currentQuestion.getExplanation());
        builder.setCancelable(false);
        builder.setPositiveButton("Next", (dialog, which) -> {
            dialog.dismiss();
            showNextQuestion();
        });
        builder.show();
    }

    private void finishQuiz() {
        DatabaseReference userRef = databaseReference.child("users").child(userId)
                .child("extraQuiz");

        userRef.get().addOnSuccessListener(snapshot -> {
            int previousScore = snapshot.child("finalScore").getValue(Integer.class) != null
                    ? snapshot.child("finalScore").getValue(Integer.class)
                    : 0;

            if (score > previousScore) {
                Map<String, Object> summaryData = new HashMap<>();
                summaryData.put("finalScore", score);
                summaryData.put("totalQuestions", TOTAL_QUESTIONS);
                summaryData.put("completionTimestamp", System.currentTimeMillis());

                userRef.setValue(summaryData)
                        .addOnFailureListener(e -> Toast.makeText(this, "Failed to save final score.", Toast.LENGTH_SHORT).show());
            }

            // Navigate to result screen
            Intent intent = new Intent(ExtraQuizActivity.this, ExtraQuizResult.class);
            intent.putExtra("SCORE", score);
            intent.putExtra("TOTAL_QUESTIONS", TOTAL_QUESTIONS);
            startActivity(intent);
            finish();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
