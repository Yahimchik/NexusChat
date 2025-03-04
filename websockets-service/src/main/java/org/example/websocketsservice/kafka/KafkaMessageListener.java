//package org.example.websocketsservice.kafka;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class KafkaMessageListener {
//    private final SimpMessagingTemplate messagingTemplate;
//
//    @KafkaListener(topics = "new-messages", groupId = "chat-service-group")
//    public void listen(String message) {
//        messagingTemplate.convertAndSend("/topic/messages", message);
//    }
//}