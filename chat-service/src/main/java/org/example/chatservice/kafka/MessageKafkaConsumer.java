package org.example.chatservice.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.chatservice.dto.message.MessageResponseDto;
import org.example.chatservice.services.MessageService;
import org.example.dto.MessageRequestDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageKafkaConsumer {

    private final MessageService messageService;

    @KafkaListener(topics = "new-messages", groupId = "chat-service-group")
    public void consumeMessage(@Payload MessageRequestDto messageRequestDto,
                               @Header("userId") String userId) {
        log.info("Получено сообщение из Kafka: " +
                        "chatId={}, " +
                        "userId={} ",
                messageRequestDto.getChatId(),
                userId);

        MessageResponseDto message = messageService.saveMessage(messageRequestDto, userId);

        try {
            log.info("Сообщение обработано и сохранено: " +
                            "chatId={}, " +
                            "messageId={}",
                    message.getChatId(),
                    message.getId());
        } catch (Exception e) {
            log.error("Ошибка при обработке сообщения: " +
                            "chatId={}, " +
                            "messageId={}, " +
                            "error={}",
                    message.getChatId(),
                    message.getId(),
                    e.getMessage());
        }
    }
}
