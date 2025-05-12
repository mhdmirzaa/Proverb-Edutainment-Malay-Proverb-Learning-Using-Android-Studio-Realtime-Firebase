package com.example.proverbapp;
import android.content.Intent;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class signup extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        EditText nameField = findViewById(R.id.yourName);
        EditText emailField = findViewById(R.id.yourEmail);
        EditText passwordField = findViewById(R.id.password);
        EditText rePasswordField = findViewById(R.id.rePassword);
        Button signupButton = findViewById(R.id.signupButton);
        TextView loginText = findViewById(R.id.loginText);

        // Highlight "Login" word in blue
        String fullText = "Already have an account? Login";
        SpannableString spannable = new SpannableString(fullText);
        int startIndex = fullText.indexOf("Login");
        int endIndex = startIndex + "Login".length();
        spannable.setSpan(new ForegroundColorSpan(Color.BLUE), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        loginText.setText(spannable);

        // Signup logic
        signupButton.setOnClickListener(view -> {
            String name = nameField.getText().toString().trim();
            String email = emailField.getText().toString().trim();
            String password = passwordField.getText().toString().trim();
            String rePassword = rePasswordField.getText().toString().trim();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || rePassword.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(rePassword)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    String userId = mAuth.getCurrentUser().getUid();
                    saveUserToFirestore(userId, name, email);
                } else {
                    Toast.makeText(this, "Signup Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        // Navigate to Login Page
        loginText.setOnClickListener(view -> {
            Intent intent = new Intent(signup.this, login.class);
            startActivity(intent);
        });
    }

    private void saveUserToFirestore(String userId, String name, String email) {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("name", name);
        userMap.put("email", email);
        userMap.put("createdAt", Timestamp.now());

        firestore.collection("users").document(userId).set(userMap)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Signup Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(signup.this, Home.class);
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error saving user data: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
