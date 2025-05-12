package com.example.proverbapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CategoryListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        // Back Button Listener
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(view -> finish()); // Closes the current activity

        // Help Button Listener
        ImageView helpButton = findViewById(R.id.helpButton);
        helpButton.setOnClickListener(view -> {
            // Show help (e.g., open a dialog or another activity)
            Log.d("CategoryListActivity", "Help button clicked!");
        });

        // Fetch categories
        Map<String, List<ProverbData.ProverbEntry>> categories = ProverbData.getProverbsByCategoryWithKeys();
        List<String> categoryNames = new ArrayList<>(categories.keySet());

        // Initialize ListView
        ListView categoryListView = findViewById(R.id.categoryListView);

        // Set up CustomAdapter
        CategoryAdapter adapter = new CategoryAdapter(categoryNames);
        categoryListView.setAdapter(adapter);

        // Handle click events
        categoryListView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedCategoryKey = categoryNames.get(position);
            String selectedCategoryTitle = categoryNames.get(position);

            Log.d("CategoryListActivity", "Selected Category: " + selectedCategoryTitle);

            Intent intent = new Intent(CategoryListActivity.this, ProverbsListActivity.class);
            intent.putExtra("categoryKey", selectedCategoryKey);
            intent.putExtra("categoryTitle", selectedCategoryTitle);
            startActivity(intent);
        });
    }

    private class CategoryAdapter extends BaseAdapter {

        private final List<String> categories;

        public CategoryAdapter(List<String> categories) {
            this.categories = categories;
        }

        @Override
        public int getCount() {
            return categories.size();
        }

        @Override
        public Object getItem(int position) {
            return categories.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(CategoryListActivity.this)
                        .inflate(R.layout.category_list_item, parent, false);
            }

            // Populate the view
            TextView courseTitle = convertView.findViewById(R.id.courseTitle);
            TextView courseDetails = convertView.findViewById(R.id.courseDetails);
            ImageView courseIcon = convertView.findViewById(R.id.courseIcon);

            String category = categories.get(position);
            courseTitle.setText(category);
            courseDetails.setText("Click details for " + category); // Replace with actual data

            // Set a placeholder icon or dynamically load based on category
            courseIcon.setImageResource(R.drawable.course);

            return convertView;
        }
    }
}
