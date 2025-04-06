package com.example.notifs.util;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Component;

@Component
public class NotificationSender {

    public void sendNotificationToToken(String token, String title, String body) {
        System.out.println("📤 Sending notification to token: " + token);
        try {
            Message message = Message.builder()
                    .setToken(token)
                    .setNotification(Notification.builder()
                            .setTitle(title)
                            .setBody(body)
                            .build())
                    .build();

            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("✅ Notification sent successfully: " + response);
        } catch (Exception e) {
            System.err.println("❌ Failed to send notification: " + e.getMessage());
        }
    }
}
