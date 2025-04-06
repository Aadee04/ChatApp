package com.example.chatapp_v2.controller;

import com.example.chatapp_v2.NotificationSenderService;
import com.example.chatapp_v2.dto.GroupMemberNotificationRequest;
import com.example.chatapp_v2.entity.ChatMessage;
import com.example.chatapp_v2.entity.Group;
import com.example.chatapp_v2.entity.GroupMember;
import com.example.chatapp_v2.entity.GroupMemberId;
import com.example.chatapp_v2.entity.MessageType;
import com.example.chatapp_v2.repository.GroupMemberRepository;
import com.example.chatapp_v2.repository.GroupRepository;
import com.example.chatapp_v2.repository.Repo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:9090", allowCredentials = "true")
@RequestMapping("/chat/groups")
public class GroupController {

    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final Repo chatMessageRepository;
    private final SimpMessageSendingOperations messagingTemplate;
    private final NotificationSenderService notificationSenderService;

    public GroupController(
            GroupRepository groupRepository,
            GroupMemberRepository groupMemberRepository,
            Repo chatMessageRepository,
            SimpMessageSendingOperations messagingTemplate,
            NotificationSenderService notificationSenderService
    ) {
        this.groupRepository = groupRepository;
        this.groupMemberRepository = groupMemberRepository;
        this.chatMessageRepository = chatMessageRepository;
        this.messagingTemplate = messagingTemplate;
        this.notificationSenderService = notificationSenderService;
    }

    // Create new group
    @PostMapping("/create")
    @ResponseBody
    public Group createGroup(@RequestParam String name) {
        Group group = new Group.GroupBuilder().name(name).build();
        groupRepository.save(group);
        return group;
    }

    // Join a group
    @PostMapping("/join")
    @ResponseBody
    public String joinGroup(@RequestParam Long groupId, @RequestParam String userId) {
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        if (groupOptional.isPresent()) {
            Group group = groupOptional.get();
            GroupMember member = new GroupMember.GroupMemberBuilder()
                    .id(new GroupMemberId(group.getId(), userId))
                    .group(group)
                    .build();
            groupMemberRepository.save(member);

            // ✅ Notify notifications-ms
            notificationSenderService.syncGroupMembership(groupId.toString(), userId);

            return "User " + userId + " joined group " + group.getName();
        } else {
            return "Group not found";
        }
    }

    // Get groups for a user
    @GetMapping("/user/{userId}")
    public @ResponseBody List<GroupMember> getUserGroups(@PathVariable String userId) {
        return groupMemberRepository.findByIdUserId(userId);
    }

    // Send message to a group
    @MessageMapping("/sendMessageToGroup")
    public void sendGroupMessage(@Payload ChatMessage chatMessage) {
        Long groupId = chatMessage.getGroup().getId();
        Optional<Group> groupOpt = groupRepository.findById(groupId);
        if (groupOpt.isPresent()) {
            chatMessageRepository.save(chatMessage);

            // WebSocket broadcast
            messagingTemplate.convertAndSend("/topic/group/" + groupId, chatMessage);

            // Push notification
            notificationSenderService.sendNotification(
                    groupId.toString(),
                    chatMessage.getSender(),
                    "New Message",
                    chatMessage.getSender() + ": " + chatMessage.getContent()
            );
        }
    }

    // When a user enters a group chat (not a permanent join)
    @MessageMapping("/addUserToGroup")
    public void addUserToGroup(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        Long groupId = chatMessage.getGroup().getId();
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        chatMessage.setType(MessageType.JOIN);
        chatMessage.setContent(chatMessage.getSender() + " joined group chat!");
        chatMessageRepository.save(chatMessage);
        messagingTemplate.convertAndSend("/topic/group/" + groupId, chatMessage);
    }

    // Get all messages for a group
    @GetMapping("/{groupId}/messages")
    public List<ChatMessage> getGroupMessages(@PathVariable Long groupId) {
        return chatMessageRepository.findByGroupId(groupId);
    }
}
