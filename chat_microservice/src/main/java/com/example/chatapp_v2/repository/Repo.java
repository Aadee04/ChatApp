package com.example.chatapp_v2.repository;

import com.example.chatapp_v2.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Repo extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByGroupId(Long groupId);
}
