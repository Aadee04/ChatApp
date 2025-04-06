package com.example.chatapp_v2.dto;


public class GroupMemberNotificationRequest {
    private Long groupId;
    private String userId;

    public GroupMemberNotificationRequest() {}

    public GroupMemberNotificationRequest(Long groupId, String userId) {
        this.groupId = groupId;
        this.userId = userId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
