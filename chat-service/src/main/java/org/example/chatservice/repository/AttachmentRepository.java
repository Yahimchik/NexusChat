package org.example.chatservice.repository;

import org.example.chatservice.entities.Attachment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AttachmentRepository extends MongoRepository<Attachment, UUID> {

    List<Attachment> findByMessageId(UUID messageId);

    List<Attachment> findBySenderId(UUID senderId);

    List<Attachment> findByChatId(UUID chatId);
}
