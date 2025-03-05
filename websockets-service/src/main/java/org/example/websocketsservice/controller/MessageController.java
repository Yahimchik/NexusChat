package org.example.websocketsservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.MessageRequestDto;
import org.example.websocketsservice.kafka.MessageKafkaProducer;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MessageController {

    private final MessageKafkaProducer messageKafkaProducer;

    @MessageMapping("/send/{chatId}")
    @SendTo("/topic/messages/{chatId}")
    public MessageRequestDto sendMessage(@PathVariable MessageRequestDto messageRequestDto, @DestinationVariable String chatId,
                                         SimpMessageHeaderAccessor headerAccessor) {
        messageRequestDto.setChatId(chatId);
        String userId = (String) headerAccessor.getSessionAttributes().get("userId");
        log.info("Получено сообщение по WebSocket: {}", messageRequestDto);
        messageKafkaProducer.sendMessage(messageRequestDto, userId);
        return messageRequestDto;
    }
}
