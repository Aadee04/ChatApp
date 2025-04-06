package com.example.notifs.controller;

import com.example.notifs.DTO.GroupMessageDTO;
import com.example.notifs.service.UserTokenService;
import com.example.notifs.util.NotificationSender;
import org.aspectj.weaver.ast.Not;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chat/notifications")
public class NotificationController {

    private final UserTokenService userTokenService;
    private final NotificationSender notificationSender;

    public NotificationController(UserTokenService userTokenService, NotificationSender notificationSender) {
        this.userTokenService = userTokenService;
        this.notificationSender = notificationSender;
    }

    @PostMapping("/add-group-member")
    public ResponseEntity<String> addGroupMember(@RequestBody Map<String, String> payload) {
        String groupId = payload.get("groupId");
        String userId = payload.get("userId");

        userTokenService.addUserToGroup(groupId, userId); // you need this method in your service

        return ResponseEntity.ok("User added to group successfully!");
    }


    @PostMapping("/register-token")
    public ResponseEntity<String> registerToken(@RequestBody Map<String, String> payload) {
        String userId = payload.get("userId");
        String fcmToken = payload.get("fcmToken");
        userTokenService.saveToken(userId, fcmToken);
        return ResponseEntity.ok("Token registered successfully!");
    }

    @GetMapping("/tokens-by-group")
    public ResponseEntity<List<String>> getTokensByGroup(
            @RequestParam String groupId,
            @RequestParam String senderId
    ) {
        List<String> tokens = userTokenService.getTokensByGroupExcludingSender(groupId, senderId);
        return ResponseEntity.ok(tokens);
    }

    @PostMapping("/send-group-message")
    public ResponseEntity<String> sendGroupMessage(@RequestBody GroupMessageDTO dto) {
        List<String> tokens = userTokenService.getTokensByGroupExcludingSender(dto.getGroupId(), dto.getSenderId());

        for (String token : tokens) {
            notificationSender.sendNotificationToToken(token, dto.getTitle(), dto.getBody()); // utility method
        }

        return ResponseEntity.ok("✅ Notifications sent to group members!");
    }




}
