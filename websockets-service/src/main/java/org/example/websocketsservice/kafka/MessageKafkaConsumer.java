package org.example.websocketsservice.kafka;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.websocketsservice.security.WebSocketSessionManager;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@EnableKafka
@Service
@RequiredArgsConstructor
public class MessageKafkaConsumer {

    private final WebSocketSessionManager webSocketSessionManager;

    @KafkaListener(topics = "new-messages", groupId = "websocket-service-group")
    public void consume(ConsumerRecord<String, String> record) {
        String message = record.value();
        String userId = record.key();
        WebSocketSession session = webSocketSessionManager.getSession(userId);
        if (session != null && session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (Exception e) {
                System.err.println("Error sending message to WebSocket session: " + e.getMessage());
            }
        }
    }
}