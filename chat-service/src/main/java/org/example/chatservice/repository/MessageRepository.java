package org.example.chatservice.repository;

import org.bson.types.ObjectId;
import org.example.chatservice.entities.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MessageRepository extends MongoRepository<Message, ObjectId> {

    List<Message> findByChatId(String chatId);

    List<Message> findByTextContainingIgnoreCase(String text);

    List<Message> findByChatIdAndReadFalse(String chatId);

    Optional<Message> findMessageById(ObjectId id);
}
