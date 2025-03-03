package org.example.chatservice.services.impl;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.example.chatservice.dto.message.MessageRequestDto;
import org.example.chatservice.dto.message.MessageResponseDto;
import org.example.chatservice.entities.Message;
import org.example.chatservice.mapper.MessageMapper;
import org.example.chatservice.security.adapter.SecurityContextAdapter;
import org.example.chatservice.services.MessageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final SecurityContextAdapter securityContextAdapter;
    private final MessageStorageService messageStorageService;
    private final MessageEventService eventService;
    private final MessageMapper messageMapper;

    @Override
    @Transactional
    public MessageResponseDto sendMessage(MessageRequestDto messageRequestDto) {
        Message message = messageMapper.messageRequestToMessage(messageRequestDto);
        message.setSenderId(String.valueOf(securityContextAdapter.getCurrentUserId()));
        message = messageStorageService.saveMessage(message);
        eventService.sendMessageEvent(messageMapper.messageToMessageResponseDto(message));
        return messageMapper.messageToMessageResponseDto(message);
    }

    @Override
    public MessageResponseDto markAsRead(ObjectId messageId) {

        Message message = messageStorageService.findMessageById(messageId);

        message.setRead(true);
        message = messageStorageService.saveMessage(message);

        return messageMapper.messageToMessageResponseDto(message);
    }

    @Override
    public MessageResponseDto deleteMessage(ObjectId messageId) {

        Message message = messageStorageService.findMessageById(messageId);

        if (isCurrentUser(UUID.fromString(message.getSenderId()))) {
            throw new IllegalArgumentException("You are not authorized to make this message");
        }

        message.setDeleted(true);
        message = messageStorageService.saveMessage(message);

        return messageMapper.messageToMessageResponseDto(message);
    }

    @Override
    public MessageResponseDto editMessage(ObjectId messageId, String newText) {

        Message message = messageStorageService.findMessageById(messageId);

        if (isCurrentUser(UUID.fromString(message.getSenderId()))) {
            throw new IllegalArgumentException("You are not authorized to make this message");
        }

        message.setText(newText);
        message.setEdited(true);
        message = messageStorageService.saveMessage(message);

        return messageMapper.messageToMessageResponseDto(message);
    }

    @Override
    public List<MessageResponseDto> getAllMessages(UUID chatId) {
        List<Message> messages = messageStorageService.findMessageByChatId(chatId);

        return messages.stream()
                .map(messageMapper::messageToMessageResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MessageResponseDto> findByText(String text) {
        List<Message> messages = messageStorageService.findMessageByText(text);

        return messages.stream()
                .map(messageMapper::messageToMessageResponseDto)
                .collect(Collectors.toList());
    }

    private boolean isCurrentUser(UUID senderId) {
        return !securityContextAdapter.getCurrentUserId().equals(senderId);
    }
}
