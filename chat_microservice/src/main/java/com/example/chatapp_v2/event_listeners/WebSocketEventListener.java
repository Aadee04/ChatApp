package com.example.chatapp_v2.event_listeners;

import com.example.chatapp_v2.entity.ChatMessage;
import com.example.chatapp_v2.entity.MessageType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@Slf4j
public class WebSocketEventListener {
    private final SimpMessageSendingOperations messagingTemplate;

    // Manually added constructor
    public WebSocketEventListener(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");

        ChatMessage message = new ChatMessage.ChatMessageBuilder()
                .type(MessageType.LEAVE)
                .content(username + " has left the GC !!")
                .sender(username)
                .build();
        messagingTemplate.convertAndSend("/topic/public",message);
    }
}
