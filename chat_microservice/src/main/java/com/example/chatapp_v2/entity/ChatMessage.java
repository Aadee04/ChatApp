package com.example.chatapp_v2.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "chat_messages")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private MessageType type;

    private String content;
    private String sender;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = true) // Nullable because it can be a private message
    private Group group; // Link to Group entity

    // Private constructor for builder
    private ChatMessage(ChatMessageBuilder builder) {
        this.id = builder.id;
        this.type = builder.type;
        this.content = builder.content;
        this.sender = builder.sender;
        this.group = builder.group;
    }

    // No-arg constructor for JPA
    public ChatMessage() {}

    // Getters
    public Long getId() {
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

    public Group getGroup() {
        return group;
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

    public void setGroup(Group group) {
        this.group = group;
    }

    // Static inner Builder class
    public static class ChatMessageBuilder {
        private Long id;
        private MessageType type;
        private String content;
        private String sender;
        private Group group;

        public ChatMessageBuilder() {}

        public ChatMessageBuilder id(Long id) {
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

        public ChatMessageBuilder group(Group group) {
            this.group = group;
            return this;
        }

        public ChatMessage build() {
            return new ChatMessage(this);
        }
    }
}
