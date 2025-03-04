package org.example.websocketsservice.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class WebSocketSessionManager {

    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    public void addSession(String userId, WebSocketSession session) {
        sessions.put(userId, session);
    }

    public WebSocketSession getSession(String userId) {
        return sessions.get(userId);
    }

    public void removeSession(String userId) {
        sessions.remove(userId);
    }

}