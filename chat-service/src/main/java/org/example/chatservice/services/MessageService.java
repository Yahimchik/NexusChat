package org.example.chatservice.services;

import org.bson.types.ObjectId;
import org.example.chatservice.dto.message.DeleteMessageDto;
import org.example.chatservice.dto.message.MessageRequestDto;
import org.example.chatservice.dto.message.MessageResponseDto;
import org.example.chatservice.entities.Message;

import java.util.List;
import java.util.UUID;

public interface MessageService {

    MessageResponseDto sendMessage(MessageRequestDto messageRequestDto);

    MessageResponseDto markAsRead(UUID messageId);

    MessageResponseDto deleteMessage(ObjectId messageId);

    MessageResponseDto editMessage(UUID messageId, String newText);

    List<MessageResponseDto> getAllMessages(UUID chatId);

}
