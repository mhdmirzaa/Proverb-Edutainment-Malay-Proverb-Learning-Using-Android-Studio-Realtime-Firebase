package com.example.proverbapp;

public class UserScore {
    private final String userId;
    private final String userName;
    private final String profileImageUrl;
    private final long totalScore;

    public UserScore(String userId, String userName, String profileImageUrl, long totalScore) {
        this.userId = userId;
        this.userName = userName;
        this.profileImageUrl = profileImageUrl;
        this.totalScore = totalScore;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public long getTotalScore() {
        return totalScore;
    }
}
