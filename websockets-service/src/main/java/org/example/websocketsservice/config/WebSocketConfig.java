//package org.example.websocketsservice.config;
//
//import lombok.RequiredArgsConstructor;
//import org.example.websocketsservice.handler.ChatWebSocketHandler;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.web.socket.config.annotation.*;
//import org.springframework.web.socket.server.standard.ServerEndpointExporter;
//
//@Configuration
//@EnableWebSocketMessageBroker
//@RequiredArgsConstructor
//public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
//
//    private final ChatWebSocketHandler chatWebSocketHandler;
//
//    @Override
//    public void configureMessageBroker(org.springframework.messaging.simp.config.MessageBrokerRegistry registry) {
//        // Конфигурация брокера сообщений
//        registry.enableSimpleBroker("/topic");  // Для сообщений, отправляемых на /topic
//        registry.setApplicationDestinationPrefixes("/app");  // Префикс для отправки сообщений
//    }
//
//    @Override
//    public void registerStompEndpoints(org.springframework.web.socket.config.annotation.StompEndpointRegistry registry) {
//        // Регистрация STOMP конечной точки WebSocket
//        registry.addEndpoint("/ws");
//    }
//}
