package org.example.chatservice.services.impl;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.example.chatservice.dto.message.MessageRequestDto;
import org.example.chatservice.dto.message.MessageResponseDto;
import org.example.chatservice.entities.Message;
import org.example.chatservice.mapper.MessageMapper;
import org.example.chatservice.repository.MessageRepository;
import org.example.chatservice.security.adapter.SecurityContextAdapter;
import org.example.chatservice.services.MessageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final SecurityContextAdapter securityContextAdapter;


    @Override
    @Transactional
    public MessageResponseDto sendMessage(MessageRequestDto messageRequestDto) {
        Message message = messageMapper.messageRequestToMessage(messageRequestDto);
        message.setSenderId(String.valueOf(securityContextAdapter.getCurrentUserId()));
        message = messageRepository.save(message);
        return messageMapper.messageToMessageResponseDto(message);
    }

    @Override
    public MessageResponseDto markAsRead(UUID messageId) {

        return null;
    }

    @Override
    public MessageResponseDto deleteMessage(ObjectId messageId) {

        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new NoSuchElementException("Message not found"));

        if (!isCurrentUser(UUID.fromString(message.getSenderId()))) {
            throw new IllegalArgumentException("You are not authorized to make this message");
        }

        message.setDeleted(true);
        message = messageRepository.save(message);

        return messageMapper.messageToMessageResponseDto(message);
    }

    @Override
    public MessageResponseDto editMessage(UUID messageId, String newText) {
        return null;
    }

    @Override
    public List<MessageResponseDto> getAllMessages(UUID chatId) {
        List<Message> messages = messageRepository.findByChatId(String.valueOf(chatId));

        return messages.stream()
                .map(messageMapper::messageToMessageResponseDto)
                .collect(Collectors.toList());
    }

    private boolean isCurrentUser(UUID senderId) {
        return securityContextAdapter.getCurrentUserId().equals(senderId);
    }
}
