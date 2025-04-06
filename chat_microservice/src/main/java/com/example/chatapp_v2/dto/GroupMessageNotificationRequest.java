package com.example.chatapp_v2.dto;


public class GroupMessageNotificationRequest {
    private String groupId;
    private String senderId;
    private String title;
    private String body;

    public GroupMessageNotificationRequest() {}

    public GroupMessageNotificationRequest(String groupId, String senderId, String title, String body) {
        this.groupId = groupId;
        this.senderId = senderId;
        this.title = title;
        this.body = body;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    // Getters and Setters
}

