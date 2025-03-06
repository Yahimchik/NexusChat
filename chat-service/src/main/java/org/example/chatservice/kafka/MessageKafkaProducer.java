package org.example.chatservice.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.chatservice.dto.message.MessageResponseDto;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@EnableKafka
@Service
@RequiredArgsConstructor
public class MessageKafkaProducer {

    private final KafkaTemplate<String, MessageResponseDto> kafkaTemplate;

    public void sendMessage(MessageResponseDto message) {
        String chatId = message.getChatId();
        kafkaTemplate.send("message-updates", chatId, message)
                .whenComplete((res, ex) -> {
                    if (ex != null) {
                        log.error("Ошибка при отправке сообщения в Kafka: " +
                                        "chatId={}, " +
                                        "messageId={}, error={}",
                                chatId, message.getId(), ex.getMessage());
                    } else {
                        log.info(
                                "Сообщение успешно отправлено в Kafka: " +
                                        "chatId={}," +
                                        " messageId={}," +
                                        " partition={}," +
                                        " offset={}",
                                chatId,
                                message.getId(),
                                res.getRecordMetadata().partition(),
                                res.getRecordMetadata().offset());
                    }
                });
    }
}
