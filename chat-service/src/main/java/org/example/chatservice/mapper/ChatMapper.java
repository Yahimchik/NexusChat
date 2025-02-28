package org.example.chatservice.mapper;

import org.example.chatservice.dto.chat.ChatResponseDto;
import org.example.chatservice.entities.Chat;
import org.example.chatservice.entities.ChatUser;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface ChatMapper {

    @Mapping(target = "userIds", expression = "java(mapChatUsers(chat.getUsers()))")
    ChatResponseDto convertToResponseDto(Chat chat);

    default List<UUID> mapChatUsers(List<ChatUser> chatUsers) {
        return chatUsers.stream()
                .map(ChatUser::getUserId)
                .toList();
    }

    @Mapping(target = "users", ignore = true)
    Chat toChat(ChatResponseDto responseDto);

}
