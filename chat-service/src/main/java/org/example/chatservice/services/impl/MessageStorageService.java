package org.example.chatservice.services.impl;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.example.chatservice.entities.Message;
import org.example.chatservice.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageStorageService {
    private final MessageRepository messageRepository;

    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    public Message findMessageById(ObjectId id) {
        return messageRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Message not found"));
    }

    public List<Message> findMessageByChatId(UUID chatId) {
        return messageRepository.findByChatId(chatId.toString());
    }

    public List<Message> findMessageByText(String text) {
        return messageRepository.findByTextContainingIgnoreCase(text);
    }
}
