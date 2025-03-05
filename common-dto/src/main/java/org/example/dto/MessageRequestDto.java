package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageRequestDto {
    private String chatId;
    private String text;

    private boolean isRead = false;
    private boolean isDeleted = false;
    private boolean isEdited = false;
    private long timestamp;
}