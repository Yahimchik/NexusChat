package org.example.websocketsservice.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.MessageResponseDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageKafkaConsumer {

    private final SimpMessagingTemplate messagingTemplate;
    private final ObjectMapper objectMapper; // Добавляем ObjectMapper

    @KafkaListener(topics = "message-updates", groupId = "websocket-service-group")
    public void consumeMessage(@Payload String message) {
        log.info("Получено сообщение из Kafka для WebSocket: {}", message);

        try {
            MessageResponseDto messageDto = objectMapper.readValue(message, MessageResponseDto.class);

            // Отправляем сообщение в WebSocket с корректным форматом
            messagingTemplate.convertAndSend("/topic/messages/" + messageDto.getChatId(), messageDto);
        } catch (Exception e) {
            log.error("Ошибка при парсинге сообщения из Kafka: {}", e.getMessage());
        }
    }
}