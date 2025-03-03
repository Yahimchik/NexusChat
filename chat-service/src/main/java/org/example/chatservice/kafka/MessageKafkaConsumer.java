package org.example.chatservice.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.chatservice.dto.message.MessageResponseDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageKafkaConsumer {

    private final SimpMessagingTemplate messagingTemplate;  // Для отправки сообщений через WebSocket

    @KafkaListener(topics = "new-messages", groupId = "chat-service-group")
    public void consumeMessage(MessageResponseDto message) {
        log.info("Получено сообщение из Kafka: {}", message);
        messagingTemplate.convertAndSend("/topic/chat/" + message.getChatId(), message);
        log.info("Сообщение отправлено через WebSocket: {}", message);
    }
}
