package org.example.chatservice.dto.reaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReactionRequestDto {
    private UUID messageId;
    private UUID userId;
    private String reactionType;
}
