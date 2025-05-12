package com.example.proverbapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Setting extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText editName;
    private Button saveButton;
    private ImageView profileImageView, backButton;

    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        // Initialize Firebase
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        databaseReference = FirebaseDatabase.getInstance("https://proverb-5dcf7-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("users");

        // Initialize views
        editName = findViewById(R.id.editName);
        saveButton = findViewById(R.id.saveButton);
        profileImageView = findViewById(R.id.profileImageView);
        backButton = findViewById(R.id.backButton);

        // Load existing user data
        loadUserData();

        // Save changes on button click
        saveButton.setOnClickListener(v -> saveUserData());

        // Back button functionality
        backButton.setOnClickListener(v -> startActivity(new Intent(Setting.this, profileActivity.class)));

        // Profile image click to delete or change
        profileImageView.setOnClickListener(v -> showProfileImageOptions());
    }

    private void loadUserData() {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(this, "User not authenticated!", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = currentUser.getUid();

        // Fetch name from Firestore
        firestore.collection("users").document(userId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String name = documentSnapshot.getString("name");
                        editName.setText(name != null ? name : "");
                    } else {
                        Toast.makeText(this, "User data not found in Firestore!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to load user data from Firestore!", Toast.LENGTH_SHORT).show());

        // Fetch profile picture from Firebase Realtime Database
        databaseReference.child(userId).child("profileImage").get()
                .addOnSuccessListener(snapshot -> {
                    if (snapshot.exists()) {
                        String profileImageBase64 = snapshot.getValue(String.class);
                        if (profileImageBase64 != null && !profileImageBase64.isEmpty()) {
                            byte[] decodedBytes = android.util.Base64.decode(profileImageBase64, android.util.Base64.DEFAULT);
                            Glide.with(this)
                                    .asBitmap()
                                    .load(decodedBytes)
                                    .circleCrop()
                                    .into(profileImageView);
                        } else {
                            profileImageView.setImageResource(R.drawable.profile); // Default placeholder
                        }
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to load profile picture!", Toast.LENGTH_SHORT).show());
    }

    private void showProfileImageOptions() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Profile Picture Options")
                .setItems(new String[]{"Change Profile Picture", "Delete Profile Picture"}, (dialog, which) -> {
                    if (which == 0) {
                        openImageChooser();
                    } else if (which == 1) {
                        deleteProfileImage();
                    }
                })
                .show();
    }

    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Profile Image"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                uploadProfileImageToFirebase(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to load image.", Toast.LENGTH_SHORT).show();
            }
        }
    }




    private void saveUserData() {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(this, "User not authenticated!", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = currentUser.getUid();
        String updatedName = editName.getText().toString().trim();

        if (TextUtils.isEmpty(updatedName)) {
            Toast.makeText(this, "Name field is required!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update Firestore data
        firestore.collection("users").document(userId).update("name", updatedName)
                .addOnSuccessListener(aVoid -> showPopupMessage("Success", "Name updated successfully!"))
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to update name!", Toast.LENGTH_SHORT).show());
    }

    private void deleteProfileImage() {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(this, "User not authenticated!", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = currentUser.getUid();
        databaseReference.child(userId).child("profileImage").removeValue()
                .addOnSuccessListener(aVoid -> {
                    profileImageView.setImageResource(R.drawable.profile); // Set default placeholder
                    showPopupMessage("Success", "Profile image deleted successfully!");
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to delete profile image.", Toast.LENGTH_SHORT).show());
    }

    private void uploadProfileImageToFirebase(Bitmap bitmap) {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(this, "User not authenticated!", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = currentUser.getUid();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String base64Image = android.util.Base64.encodeToString(imageBytes, android.util.Base64.DEFAULT);

        // Save the profile image in Firebase
        databaseReference.child(userId).child("profileImage").setValue(base64Image)
                .addOnSuccessListener(aVoid -> {
                    Glide.with(this)
                            .asBitmap()
                            .load(imageBytes)
                            .circleCrop()
                            .into(profileImageView);
                    showPopupMessage("Success", "Profile image updated successfully!");
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to upload profile image.", Toast.LENGTH_SHORT).show());
    }

    // Helper method to show a popup message
    private void showPopupMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .setCancelable(false)
                .show();
    }

}
