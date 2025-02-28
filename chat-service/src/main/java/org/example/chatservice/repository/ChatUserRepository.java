package org.example.chatservice.repository;

import org.example.chatservice.entities.ChatUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChatUserRepository extends JpaRepository<ChatUser, UUID> {

    List<ChatUser> findByChatId(UUID chatId);

    Optional<ChatUser> findByChatIdAndUserId(UUID chatId, UUID userId);

    void deleteByChatIdAndUserId(UUID chatId, UUID userId);
}
