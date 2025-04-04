package com.example.chatapp_v2.repository;

import com.example.chatapp_v2.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface Repo extends JpaRepository<ChatMessage, UUID> {
}
