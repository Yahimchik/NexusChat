package org.example.chatservice.mapper;

import org.example.chatservice.dto.message.MessageRequestDto;
import org.example.chatservice.dto.message.MessageResponseDto;
import org.example.chatservice.entities.Message;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface MessageMapper {

    @Mapping(target = "timestamp", expression = "java(System.currentTimeMillis())")
    Message messageRequestToMessage(MessageRequestDto messageRequestDto);

    MessageResponseDto messageToMessageResponseDto(Message message);

    List<MessageResponseDto> messageToMessageResponseDtoList(List<Message> messages);
}
