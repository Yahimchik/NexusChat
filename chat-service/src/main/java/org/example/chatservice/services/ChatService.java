package org.example.chatservice.services;

import org.example.chatservice.dto.chat.ChatRequestDto;
import org.example.chatservice.dto.chat.ChatResponseDto;

import java.util.List;
import java.util.UUID;

public interface ChatService {

    ChatResponseDto createChat(ChatRequestDto chatRequestDto);

    ChatResponseDto getChatById(UUID chatId);

    List<ChatResponseDto> getUserChats(UUID userId);

    void addUserToChat(UUID chatId);

    void deleteChat(UUID chatId);

    void removeUserFromChat(UUID chatId);
}
