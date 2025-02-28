package org.example.chatservice.entities;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "attachments")
public class Attachment {

    @Id
    private UUID id;

    private UUID chatId;
    private UUID messageId;
    private UUID senderId;

    private String fileName;
    private String fileType;
    private String fileUrl;

    private long timestamp;
}
