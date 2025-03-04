package org.example.websocketsservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.crypto.SecretKey;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class JwtHandshakeInterceptor implements HandshakeInterceptor {
    private final SecretKey SECRET_KEY; // Нужно хранить в конфиге

    public JwtHandshakeInterceptor() {
        this.SECRET_KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode("vJAJGzMCvBxRLrHx6ufd3aPQj1SZhs8x0cROA+WzI9GUIRr2h4FN/yNUsdYTyZH1ijZzY4QlS/wZI2NFKZy4/g=="));
    }

    @Override
    public boolean beforeHandshake(
            ServerHttpRequest request, ServerHttpResponse response,
            WebSocketHandler wsHandler, Map<String, Object> attributes) {

        String token = extractToken(request);
        if (token != null) {
            String userId = extractUserIdFromToken(token);
            if (userId != null) {
                attributes.put("userId", userId);
                return true;
            }
        }
        return false;
    }

    @Override
    public void afterHandshake(
            ServerHttpRequest request, ServerHttpResponse response,
            WebSocketHandler wsHandler, Exception exception) {
    }

    private String extractToken(ServerHttpRequest request) {
        List<String> headers = request.getHeaders().get(HttpHeaders.AUTHORIZATION);
        if (headers != null && !headers.isEmpty()) {
            return headers.get(0).replace("Bearer ", "");
        }
        return null;
    }

    private String extractUserIdFromToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (Exception e) {
            log.error("Invalid JWT token", e);
            return null;
        }
    }
}
