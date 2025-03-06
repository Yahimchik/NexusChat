package org.example.chatservice.services;

import org.bson.types.ObjectId;
import org.example.chatservice.dto.message.MessageResponseDto;
import org.example.dto.MessageRequestDto;

import java.util.List;
import java.util.UUID;

public interface MessageService {

    MessageResponseDto saveMessage(MessageRequestDto messageRequestDto, String userId);

    MessageResponseDto markAsRead(ObjectId messageId);

    MessageResponseDto deleteMessage(ObjectId messageId);

    MessageResponseDto editMessage(ObjectId messageId, String newText);

    List<MessageResponseDto> getAllMessages(UUID chatId);

    List<MessageResponseDto> findByText(String text);


}
