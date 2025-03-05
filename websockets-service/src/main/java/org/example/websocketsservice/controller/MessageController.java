package org.example.websocketsservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.MessageRequestDto;
import org.example.websocketsservice.kafka.MessageKafkaProducer;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MessageController {

    private final MessageKafkaProducer messageKafkaProducer;

    @MessageMapping("/send/{chatId}")
    @SendTo("/topic/messages/{chatId}")
    public MessageRequestDto sendMessage(@PathVariable MessageRequestDto messageRequestDto, @DestinationVariable String chatId) {
        messageRequestDto.setChatId(chatId);
        log.info("Получено сообщение по WebSocket: {}", messageRequestDto);
        messageKafkaProducer.sendMessage(messageRequestDto);
        return messageRequestDto;
    }

}
