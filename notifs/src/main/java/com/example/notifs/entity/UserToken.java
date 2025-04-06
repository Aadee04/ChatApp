package com.example.notifs.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class UserToken {
    @Id
    private String userId;
    private String fcmToken;

    // Constructors
    public UserToken() {}
    public UserToken(String userId, String fcmToken) {
        this.userId = userId;
        this.fcmToken = fcmToken;
    }

    // Getters & Setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getFcmToken() { return fcmToken; }
    public void setFcmToken(String fcmToken) { this.fcmToken = fcmToken; }
}
