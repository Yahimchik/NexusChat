package org.example.chatservice.entities;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.chatservice.entities.enums.ReactionType;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "reactions")
public class Reaction {
    @Id
    private UUID id;

    private UUID messageId;
    private UUID userId;
    @Field("reaction_type")
    private ReactionType type;

    private long timestamp;
}
