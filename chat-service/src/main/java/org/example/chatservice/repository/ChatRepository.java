package org.example.chatservice.repository;

import org.example.chatservice.entities.Chat;
import org.example.chatservice.entities.enums.ChatType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChatRepository extends JpaRepository<Chat, UUID> {

    Optional<Chat> findChatByName(String name);

    List<Chat> findChatByType(ChatType type);

    List<Chat> findByUsers_UserId(UUID userId);

}
