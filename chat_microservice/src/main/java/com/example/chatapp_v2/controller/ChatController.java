package com.example.chatapp_v2.controller;

import com.example.chatapp_v2.entity.ChatMessage;
import com.example.chatapp_v2.entity.MessageType;
import com.example.chatapp_v2.repository.Repo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ChatController {

    private final Repo chatMessageRepository;
    private final SimpMessageSendingOperations messagingTemplate;

    public ChatController(Repo chatMessageRepository, SimpMessageSendingOperations messagingTemplate) {
        this.chatMessageRepository = chatMessageRepository;
        this.messagingTemplate = messagingTemplate;
    }

    @GetMapping("/me")
    public ResponseEntity<String> getUsername(HttpServletRequest request) {
        String username = (String) request.getAttribute("username"); // Extracted in JwtAuthFilter
        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }
        return ResponseEntity.ok(username);
    }

    @MessageMapping("/sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage){
        chatMessageRepository.save(chatMessage);
        return chatMessage;
    }

    @MessageMapping("/addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());

        chatMessage.setType(MessageType.JOIN);
        chatMessage.setContent(chatMessage.getSender() + " joined the chat!");
        chatMessageRepository.save(chatMessage);

        List<ChatMessage> previousMessages = chatMessageRepository.findAll();

        messagingTemplate.convertAndSend("/topic/"+chatMessage.getSender(),previousMessages);

        return chatMessage;
    }

}
