package com.example.chatapp_v2;

import com.example.chatapp_v2.dto.GroupMemberNotificationRequest;
import com.example.chatapp_v2.dto.GroupMessageNotificationRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationSenderService {

    private final RestTemplate restTemplate;

    @Value("${notifications.service.url}")
    private String notisServiceUrl;

    public NotificationSenderService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void sendNotification(String groupId, String senderId, String title, String body) {
        GroupMessageNotificationRequest request = new GroupMessageNotificationRequest(groupId, senderId, title, body);
        String url = notisServiceUrl + "/chat/notifications/send-group-message";
        restTemplate.postForEntity(url, request, Void.class);
    }

    public void syncGroupMembership(String groupId, String userId) {
        GroupMemberNotificationRequest request = new GroupMemberNotificationRequest(Long.parseLong(groupId), userId);
        String url = notisServiceUrl + "/chat/notifications/add-group-member";
        restTemplate.postForEntity(url, request, Void.class);
    }
}
