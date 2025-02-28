package org.example.chatservice.repository;

import org.example.chatservice.entities.Reaction;
import org.example.chatservice.entities.enums.ReactionType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReactionRepository extends MongoRepository<Reaction, UUID> {

    List<Reaction> findByMessageId(UUID messageId);

    Optional<Reaction> findByMessageIdAndUserId(UUID messageId, UUID userId);

    List<Reaction> findByMessageIdAndType(UUID messageId, ReactionType type);
}
