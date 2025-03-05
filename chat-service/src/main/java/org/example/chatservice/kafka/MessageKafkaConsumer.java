package org.example.chatservice.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.chatservice.dto.message.MessageResponseDto;
import org.example.chatservice.services.MessageService;
import org.example.dto.MessageRequestDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageKafkaConsumer {

    private final MessageService messageService;

    @KafkaListener(topics = "new-messages", groupId = "chat-service-group")
    public void consumeMessage(MessageRequestDto messageRequestDto) {
        log.info("Получено сообщение из Kafka: " +
                        "chatId={}, ",
                messageRequestDto.getChatId());

        MessageResponseDto message = messageService.saveMessage(messageRequestDto);

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

//    @KafkaListener(topics = "new-messages", groupId = "chat-service-group")
//    public void consumeMessage(MessageResponseDto messageResponseDto) {
//        log.info("Получено сообщение из Kafka: " +
//                        "chatId={}, ",
//                messageResponseDto.getChatId());
//
//
//        try {
//            log.info("Сообщение обработано и сохранено: " +
//                            "chatId={}, " +
//                            "messageId={}",
//                    messageResponseDto.getChatId(),
//                    messageResponseDto.getId());
//        } catch (Exception e) {
//            log.error("Ошибка при обработке сообщения: " +
//                            "chatId={}, " +
//                            "messageId={}, " +
//                            "error={}",
//                    messageResponseDto.getChatId(),
//                    messageResponseDto.getId(),
//                    e.getMessage());
//        }
//
//    }
}
