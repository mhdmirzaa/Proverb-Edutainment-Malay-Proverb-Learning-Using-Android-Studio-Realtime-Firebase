package com.example.proverbapp;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proverbapp.UserScore;

import java.util.List;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.ViewHolder> {

    private final List<UserScore> userScores;

    public LeaderboardAdapter(List<UserScore> userScores) {
        this.userScores = userScores;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_leaderboard, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserScore userScore = userScores.get(position);

        // Set position number starting from 4
        holder.numberTextView.setText(String.valueOf(position + 4)); // Add 4 to the zero-based position

        holder.nameTextView.setText(userScore.getUserName() != null ? userScore.getUserName() : "Anonymous");
        holder.scoreTextView.setText(String.valueOf(userScore.getTotalScore()));

        if (userScore.getProfileImageUrl() != null && !userScore.getProfileImageUrl().isEmpty()) {
            Glide.with(holder.profileImageView.getContext())
                    .load(Base64.decode(userScore.getProfileImageUrl(), Base64.DEFAULT))
                    .circleCrop() // Apply circle crop transformation
                    .placeholder(R.drawable.profile) // Placeholder image
                    .into(holder.profileImageView);
        } else {
            holder.profileImageView.setImageResource(R.drawable.profile); // Default placeholder
        }
    }


    @Override
    public int getItemCount() {
        return userScores.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profileImageView;
        TextView nameTextView, scoreTextView, numberTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            numberTextView = itemView.findViewById(R.id.numberTextView);
            profileImageView = itemView.findViewById(R.id.profileImageView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            scoreTextView = itemView.findViewById(R.id.scoreTextView);
        }
    }

    public void updateData(List<UserScore> updatedScores) {
        Log.d("LeaderboardAdapter", "Updating with users: " + updatedScores.size());
        this.userScores.clear();
        this.userScores.addAll(updatedScores);
        notifyDataSetChanged();
    }


}
