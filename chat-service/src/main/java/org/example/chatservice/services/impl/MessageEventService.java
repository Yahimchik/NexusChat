package org.example.chatservice.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.chatservice.dto.message.MessageResponseDto;
import org.example.chatservice.kafka.MessageKafkaProducer;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageEventService {

    private final MessageKafkaProducer kafkaProducer;

    public void sendMessageEvent(MessageResponseDto messageResponseDto) {
        log.info("Publishing message event to Kafka: {}", messageResponseDto);
        kafkaProducer.sendMessage(messageResponseDto);
    }
}
