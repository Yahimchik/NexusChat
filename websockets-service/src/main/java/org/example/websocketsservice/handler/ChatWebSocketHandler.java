package org.example.websocketsservice.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.websocketsservice.security.WebSocketSessionManager;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

@Component
@Slf4j
@RequiredArgsConstructor
public class ChatWebSocketHandler extends AbstractWebSocketHandler {

    private final WebSocketSessionManager webSocketSessionManager;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userId = getUserIdFromSession(session);
        log.info("User connected: {}, sessionId: {}", userId, session.getId());
        webSocketSessionManager.addSession(userId, session);
    }


    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        if (payload.contains("AUTH")) {
            // Извлекаем токен
            String token = extractTokenFromAuthMessage(payload);
            // Дальше использовать токен для проверки
        }
    }

    private String extractTokenFromAuthMessage(String message) {
        // Логика извлечения токена из сообщения
        return message.split(":")[1].replace("}", "").trim();
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String userId = getUserIdFromSession(session);  // Извлекаем userId из сессии
        webSocketSessionManager.removeSession(userId);  // Удаляем сессию из менеджера
        log.info("User disconnected: {}", userId);
    }

    private String getUserIdFromSession(WebSocketSession session) {
        return (String) session.getAttributes().get("userId");
    }

}