package com.example.proverbapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class profileActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PICK_BANNER_REQUEST = 2;

    private TextView userNameTextView, scoreTextView;
    private ImageView profileImageView, bannerImageView;
    private RecyclerView answeredProverbsRecyclerView;

    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;

    private List<String> answeredProverbs;
    private Map<String, String> proverbMap; // Mapping of IDs to proverbs

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Back Button Listener
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(view -> finish()); // Closes the current activity

        // Initialize views
        ImageView setting = findViewById(R.id.setting);
        ImageView homeMenu = findViewById(R.id.menu_home);
        ImageView chartButton = findViewById(R.id.menu_leaderboard);
        ImageView profileButton = findViewById(R.id.menu_profile);
        userNameTextView = findViewById(R.id.userNameTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        profileImageView = findViewById(R.id.profileImageView);
        bannerImageView = findViewById(R.id.bannerImageView);
        answeredProverbsRecyclerView = findViewById(R.id.answeredProverbsRecyclerView);

        // New: Initialize the email TextView
        TextView emailTextView = findViewById(R.id.email);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        databaseReference = FirebaseDatabase.getInstance("https://proverb-5dcf7-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("users");

        answeredProverbsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        answeredProverbs = new ArrayList<>();
        proverbMap = getProverbMapFromProverbData(); // Load proverb map from ProverbData

        // Fetch and display the user's email
        String email = auth.getCurrentUser() != null ? auth.getCurrentUser().getEmail() : "No Email Found";
        emailTextView.setText(email);

        // Bottom Navigation Actions
        homeMenu.setOnClickListener(v -> {
            Intent intent = new Intent(profileActivity.this, Home.class);
            startActivity(intent);
        });

        chartButton.setOnClickListener(v -> {
            Intent intent = new Intent(profileActivity.this, LeaderboardActivity.class);
            startActivity(intent);
        });

        profileButton.setOnClickListener(v -> {
            Toast.makeText(profileActivity.this, "You are already on the Profile page.", Toast.LENGTH_SHORT).show();
        });

        // Setting Navigation Actions
        setting.setOnClickListener(v -> {
            Intent intent = new Intent(profileActivity.this, Setting.class);
            startActivity(intent);
        });
        answeredProverbsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        answeredProverbs = new ArrayList<>();
        proverbMap = getProverbMapFromProverbData(); // Load proverb map from ProverbData

        // Add spacing between items in the RecyclerView
        answeredProverbsRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                int position = parent.getChildAdapterPosition(view);
                if (position != RecyclerView.NO_POSITION) {
                    outRect.top = 16;  // Add 16dp spacing on top
                    outRect.bottom = 16; // Add 16dp spacing at the bottom
                }
            }
        });

        // Load other profile data
        loadUserNameFromFirestore();
        loadProfileImages();
        loadAnsweredProverbs();

        profileImageView.setOnClickListener(v -> openImageChooser(PICK_IMAGE_REQUEST));
        bannerImageView.setOnClickListener(v -> openImageChooser(PICK_BANNER_REQUEST));
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
                        userNameTextView.setText(userName != null ? userName : "Anonymous");
                    } else {
                        Toast.makeText(this, "User not found in Firestore!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to fetch user name from Firestore.", Toast.LENGTH_SHORT).show());
    }

    private void loadProfileImages() {
        String userId = auth.getCurrentUser() != null ? auth.getCurrentUser().getUid() : null;

        if (userId == null) {
            Toast.makeText(this, "User not authenticated!", Toast.LENGTH_SHORT).show();
            return;
        }

        databaseReference.child(userId).get()
                .addOnSuccessListener(snapshot -> {
                    if (snapshot.exists()) {
                        String profileImageBase64 = snapshot.child("profileImage").getValue(String.class);
                        if (profileImageBase64 != null) {
                            byte[] decodedBytes = Base64.decode(profileImageBase64, Base64.DEFAULT);
                            Glide.with(this)
                                    .asBitmap()
                                    .load(decodedBytes)
                                    .circleCrop() // Apply circle crop
                                    .into(profileImageView);
                        } else {
                            profileImageView.setImageResource(R.drawable.profile); // Default placeholder
                        }

                        String bannerImageBase64 = snapshot.child("bannerImage").getValue(String.class);
                        if (bannerImageBase64 != null) {
                            byte[] decodedBytes = Base64.decode(bannerImageBase64, Base64.DEFAULT);
                            Glide.with(this)
                                    .asBitmap()
                                    .load(decodedBytes)
                                    .centerCrop()
                                    .into(bannerImageView);
                        } else {
                            bannerImageView.setImageResource(R.drawable.banner); // Default placeholder
                        }

                        // Calculate total score
                        long totalScore = 0;
                        DataSnapshot quizProgressSnapshot = snapshot.child("quizProgress");
                        for (DataSnapshot categorySnapshot : quizProgressSnapshot.getChildren()) {
                            Long score = categorySnapshot.child("score").getValue(Long.class);
                            if (score != null) {
                                totalScore += score;
                            }
                        }
                        scoreTextView.setText("Total Score : " + totalScore);

                    } else {
                        Toast.makeText(this, "Total Score: 0", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to fetch profile data.", Toast.LENGTH_SHORT).show());
    }

    private void loadAnsweredProverbs() {
        String userId = auth.getCurrentUser() != null ? auth.getCurrentUser().getUid() : null;

        if (userId == null) {
            Toast.makeText(this, "User not authenticated!", Toast.LENGTH_SHORT).show();
            return;
        }

        databaseReference.child(userId).get().addOnSuccessListener(snapshot -> {
            if (snapshot.exists()) {
                answeredProverbs.clear();

                // Fetch answered proverbs for the current user
                DataSnapshot answeredProverbsSnapshot = snapshot.child("answeredProverbs");
                DataSnapshot quizProgressSnapshot = snapshot.child("quizProgress");

                // Iterate through the user's answered proverbs
                for (DataSnapshot proverbIdSnapshot : answeredProverbsSnapshot.getChildren()) {
                    String proverbId = proverbIdSnapshot.getKey();
                    Boolean isAnswered = proverbIdSnapshot.getValue(Boolean.class);

                    if (Boolean.TRUE.equals(isAnswered) && proverbMap.containsKey(proverbId)) {
                        // Fetch the actual proverb text from ProverbData
                        String proverbText = proverbMap.get(proverbId);

                        // Match timestamp from quizProgress
                        Long timestamp = null;
                        for (DataSnapshot categorySnapshot : quizProgressSnapshot.getChildren()) {
                            if (categorySnapshot.hasChild("timestamp")) {
                                timestamp = categorySnapshot.child("timestamp").getValue(Long.class);
                                break; // Stop iterating once we find a timestamp
                            }
                        }

                        if (timestamp != null) {
                            // Format the timestamp into a readable date
                            String formattedDate = formatTimestamp(timestamp);

                            // Add the proverb and timestamp to the list
                            answeredProverbs.add("Proverb: " + proverbText + "\nTime: " + formattedDate);
                        }
                    }
                }

                AnsweredProverbsAdapter adapter = new AnsweredProverbsAdapter(answeredProverbs);
                answeredProverbsRecyclerView.setAdapter(adapter);
            } else {
                Toast.makeText(this, "No answered proverbs found!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Failed to fetch answered proverbs.", Toast.LENGTH_SHORT).show();
        });
    }

    private String formatTimestamp(long timestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date(timestamp);
        return dateFormat.format(date);
    }

    static class AnsweredProverbsAdapter extends RecyclerView.Adapter<AnsweredProverbsAdapter.ViewHolder> {

        private final List<String> answeredProverbs;

        public AnsweredProverbsAdapter(List<String> answeredProverbs) {
            this.answeredProverbs = answeredProverbs;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // Inflate item_proverb.xml instead of simple_list_item_1
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_proverb, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            String answeredProverb = answeredProverbs.get(position);

            // Split proverb text and timestamp if needed
            String[] parts = answeredProverb.split("\nTime: ");
            String proverbText = parts[0].replace("Proverb: ", "");
            String timestamp = parts.length > 1 ? parts[1] : "No timestamp available";

            holder.proverbTextView.setText(proverbText);
            holder.proverbTimestampTextView.setText(timestamp);
        }

        @Override
        public int getItemCount() {
            return answeredProverbs.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            TextView proverbTextView, proverbTimestampTextView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                proverbTextView = itemView.findViewById(R.id.proverbTextView);
                proverbTimestampTextView = itemView.findViewById(R.id.proverbTimestampTextView);
            }
        }
    }


    private void openImageChooser(int requestCode) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

                if (requestCode == PICK_IMAGE_REQUEST) {
                    saveImageToDatabase(bitmap, "profileImage", profileImageView);
                } else if (requestCode == PICK_BANNER_REQUEST) {
                    saveImageToDatabase(bitmap, "bannerImage", bannerImageView);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to load image.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveImageToDatabase(Bitmap bitmap, String fieldName, ImageView imageView) {
        String userId = auth.getCurrentUser() != null ? auth.getCurrentUser().getUid() : null;

        if (userId == null) {
            Toast.makeText(this, "User not authenticated!", Toast.LENGTH_SHORT).show();
            return;
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String base64Image = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        databaseReference.child(userId).child(fieldName).setValue(base64Image)
                .addOnSuccessListener(aVoid -> {
                    imageView.setImageBitmap(bitmap);
                    Toast.makeText(this, "Image saved successfully!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to save image.", Toast.LENGTH_SHORT).show());
    }

    private Map<String, String> getProverbMapFromProverbData() {
        Map<String, String> map = new HashMap<>();
        for (List<ProverbData.ProverbEntry> entries : ProverbData.getProverbsByCategoryWithKeys().values()) {
            for (ProverbData.ProverbEntry entry : entries) {
                map.put(entry.getKey(), entry.getProverb());
            }
        }
        return map;
    }
}
