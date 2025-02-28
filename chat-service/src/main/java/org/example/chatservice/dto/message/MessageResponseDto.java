package org.example.chatservice.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageResponseDto {
    private Object id;
    private String chatId;
    private String senderId;
    private String text;

    private boolean isRead;
    private boolean isDeleted;
    private boolean isEdited;
    private long timestamp;
    private long lastModified;
}
