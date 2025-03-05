package org.example.websocketsservice.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtChannelInterceptor implements ChannelInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        // Проверяем только при CONNECT
        if (accessor.getCommand() != null && accessor.getCommand().name().equals("CONNECT")) {
            List<String> authHeaders = accessor.getNativeHeader("Authorization");

            String token = Optional.ofNullable(authHeaders)
                    .filter(headers -> !headers.isEmpty())
                    .map(headers -> headers.get(0).replace("Bearer ", ""))
                    .orElse(null);

            if (token != null) {
                String userId = jwtTokenProvider.getUserIdFromToken(token);
                log.info("WebSocket подключение от userId={}", userId);

                // Устанавливаем Principal в STOMP сессию
                accessor.setUser(() -> userId);
                accessor.getSessionAttributes().put("userId", userId);
                ;
            } else {
                log.warn("WebSocket подключение без валидного токена");
                return null; // Блокируем соединение
            }
        }
        return message;
    }
}