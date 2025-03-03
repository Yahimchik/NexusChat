package org.example.chatservice.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.chatservice.dto.message.MessageResponseDto;
import org.example.chatservice.entities.Message;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@EnableKafka
@Service
@RequiredArgsConstructor
public class MessageKafkaProducer {

    private final KafkaTemplate<String, MessageResponseDto> kafkaTemplate;

    public void sendMessage(MessageResponseDto message) {
        log.info("Sending message: {}", message);
        kafkaTemplate.send("new-messages", message);
    }
}
