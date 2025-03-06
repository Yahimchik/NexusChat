package org.example.websocketsservice.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.MessageRequestDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageKafkaProducer {

    private final KafkaTemplate<String, MessageRequestDto> kafkaTemplate;

    public void sendMessage(MessageRequestDto messageRequestDto, String userId) {
        log.info("Отправка сообщения в Kafka: chatId={}, text={}", messageRequestDto.getChatId(), messageRequestDto.getText());
        Message<MessageRequestDto> message = MessageBuilder
                .withPayload(messageRequestDto)
                .setHeader(KafkaHeaders.TOPIC, "new-messages")
                .setHeader("userId", userId)
                .build();
        kafkaTemplate.send(message);
    }
}
