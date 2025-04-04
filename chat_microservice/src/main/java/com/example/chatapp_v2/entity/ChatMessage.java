package com.example.chatapp_v2.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "chat_messages")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // Auto-generates unique UUIDs
    private UUID id;

    @Enumerated(EnumType.STRING)
    private MessageType type;

    private String content;
    private String sender;

    // Private constructor for builder
    private ChatMessage(ChatMessageBuilder builder) {
        this.id = builder.id;
        this.type = builder.type;
        this.content = builder.content;
        this.sender = builder.sender;
    }

    // No-arg constructor for JPA
    public ChatMessage() {}

    // Getters
    public UUID getId() {
        return id;
    }

    public MessageType getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public String getSender() {
        return sender;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    // Static inner Builder class
    public static class ChatMessageBuilder {
        private UUID id;
        private MessageType type;
        private String content;
        private String sender;

        public ChatMessageBuilder() {}

        public ChatMessageBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public ChatMessageBuilder type(MessageType type) {
            this.type = type;
            return this;
        }

        public ChatMessageBuilder content(String content) {
            this.content = content;
            return this;
        }

        public ChatMessageBuilder sender(String sender) {
            this.sender = sender;
            return this;
        }

        public ChatMessage build() {
            return new ChatMessage(this);
        }
    }
}
