package org.example.chatservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "messages")
public class Message {
    @Id
    private ObjectId id;

    private String chatId;

    private String senderId;

    private String text;

    @Field("is_read")
    private boolean isRead;

    @Field("is_deleted")
    private boolean isDeleted;

    @Field("is_edited")
    private boolean isEdited;

    private long timestamp;
}
