package org.example.chatservice.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.chatservice.dto.chat.ChatRequestDto;
import org.example.chatservice.dto.chat.ChatResponseDto;
import org.example.chatservice.entities.Chat;
import org.example.chatservice.entities.ChatUser;
import org.example.chatservice.entities.enums.ChatType;
import org.example.chatservice.entities.enums.UserRole;
import org.example.chatservice.mapper.ChatMapper;
import org.example.chatservice.repository.ChatRepository;
import org.example.chatservice.repository.ChatUserRepository;
import org.example.chatservice.security.adapter.SecurityContextAdapter;
import org.example.chatservice.services.ChatService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final SecurityContextAdapter securityContextAdapter;
    private final ChatUserRepository chatUserRepository;

    private final ChatRepository chatRepository;
    private final ChatMapper chatMapper;

    @Override
    @Transactional
    public ChatResponseDto createChat(ChatRequestDto chatRequestDto) {
        Chat chat = Chat.builder()
                .name(chatRequestDto.getName())
                .type(ChatType.valueOf(chatRequestDto.getType()))
                .build();

        Chat finalChat = chatRepository.save(chat);

        List<ChatUser> chatUsers = chatRequestDto.getUserIds().stream()
                .map(userId -> createChatUser(finalChat, userId))
                .toList();

        chatUserRepository.saveAll(chatUsers);
        chat.setUsers(chatUsers);

        return chatMapper.convertToResponseDto(chat);
    }

    @Override
    @Transactional
    public ChatResponseDto getChatById(UUID chatId) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new EntityNotFoundException("Chat not found"));
        return chatMapper.convertToResponseDto(chat);
    }

    @Override
    @Transactional
    public List<ChatResponseDto> getUserChats(UUID userId) {
        return chatRepository.findAll().stream()
                .map(chatMapper::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void addUserToChat(UUID chatId) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new EntityNotFoundException("Chat not found"));

        UUID userId = securityContextAdapter.getCurrentUserId();

        if (chatUserRepository.findByChatIdAndUserId(chatId, userId).isPresent()) {
            throw new IllegalStateException("User with id " + userId + " already exists");
        }

        ChatUser user = createChatUser(chat, userId);

        chatUserRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteChat(UUID chatId) {
        if (!chatRepository.existsById(chatId)) {
            throw new EntityNotFoundException("Chat not found");
        }
        chatRepository.deleteById(chatId);
    }

    @Override
    @Transactional
    public void removeUserFromChat(UUID chatId) {
        UUID userId = securityContextAdapter.getCurrentUserId();
        chatUserRepository.deleteByChatIdAndUserId(chatId, userId);
    }

    private ChatUser createChatUser(Chat chat, UUID userId) {
        return ChatUser.builder()
                .chat(chat)
                .userId(userId)
                .userRole(UserRole.MEMBER)
                .build();
    }
}
