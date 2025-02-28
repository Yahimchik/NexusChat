package org.example.chatservice.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.chatservice.entities.enums.ChatType;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatResponseDto {
    private UUID id;
    private String name;
    private ChatType type;
    private List<UUID> userIds;
}
