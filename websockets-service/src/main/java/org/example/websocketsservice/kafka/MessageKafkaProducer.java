package org.example.websocketsservice.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.MessageRequestDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageKafkaProducer {

    private final KafkaTemplate<String, MessageRequestDto> kafkaTemplate;

    public void sendMessage(MessageRequestDto messageRequestDto) {
        log.info("Отправка сообщения в Kafka: chatId={}, text={}", messageRequestDto.getChatId(), messageRequestDto.getText());
        kafkaTemplate.send("new-messages", messageRequestDto.getChatId(), messageRequestDto);
    }
}
