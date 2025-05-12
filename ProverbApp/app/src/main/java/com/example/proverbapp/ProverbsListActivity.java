package com.example.proverbapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProverbsListActivity extends AppCompatActivity {

    private ListView proverbsListView;
    private Button continueLearningButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proverbs_list);

        // Initialize UI elements
        ImageView backButton = findViewById(R.id.backButton);
        TextView courseTitle = findViewById(R.id.courseTitle);
        proverbsListView = findViewById(R.id.proverbsListView);
        continueLearningButton = findViewById(R.id.continueLearningButton);

        // Get the category title or key passed from the previous activity
        String categoryTitle = getIntent().getStringExtra("categoryTitle"); // User-friendly title
        String categoryKey = getIntent().getStringExtra("categoryKey"); // For fetching proverbs

        // Set course title dynamically
        if (categoryTitle != null) {
            courseTitle.setText(categoryTitle);
        } else {
            courseTitle.setText("Selected Proverb Category");
        }

        // Back button functionality
        backButton.setOnClickListener(v -> finish());

        // Fetch proverbs for the selected category
        Map<String, List<ProverbData.ProverbEntry>> categorizedProverbs = ProverbData.getProverbsByCategoryWithKeys();
        List<ProverbData.ProverbEntry> proverbs = categorizedProverbs.get(categoryKey);

        List<String> proverbTexts = new ArrayList<>();
        if (proverbs != null) {
            for (ProverbData.ProverbEntry entry : proverbs) {
                proverbTexts.add(entry.getProverb());
            }
        }

        // Set up custom adapter for proverbs
        ProverbListAdapter adapter = new ProverbListAdapter(this, proverbTexts);
        proverbsListView.setAdapter(adapter);

        // Handle list item clicks
        proverbsListView.setOnItemClickListener((parent, view, position, id) -> {
            if (proverbs != null) {
                ProverbData.ProverbEntry selectedEntry = proverbs.get(position);

                // Open detail activity
                Intent intent = new Intent(ProverbsListActivity.this, ProverbDetailActivity.class);
                intent.putExtra("proverb", selectedEntry.getProverb());
                intent.putExtra("explanation", selectedEntry.getExplanation());
                intent.putExtra("currentProverb", position + 1);
                intent.putExtra("totalProverbs", proverbs.size());

                startActivity(intent);
            }
        });

        // Handle continue learning button click
        continueLearningButton.setOnClickListener(v -> {
            // Add logic for "Continue Learning"
            finish();
        });
    }
}
