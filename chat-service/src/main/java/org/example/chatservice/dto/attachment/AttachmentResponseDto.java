package org.example.chatservice.dto.attachment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttachmentResponseDto {
    private UUID id;
    private UUID chatId;  // chatId для связи с чатом
    private UUID messageId;
    private UUID senderId;
    private String fileName;
    private String fileType;
    private String fileUrl;
    private long timestamp;  // timestamp, когда вложение было добавлено
}