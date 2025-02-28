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
public class AttachmentRequestDto {
    private UUID chatId;
    private UUID messageId;
    private UUID senderId;
    private String fileName;
    private String fileType;
    private String fileUrl;
}
