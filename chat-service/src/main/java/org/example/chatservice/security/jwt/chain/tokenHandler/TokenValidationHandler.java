package org.example.chatservice.security.jwt.chain.tokenHandler;

import io.jsonwebtoken.JwtException;
import org.example.chatservice.security.jwt.chain.JwtValidationHandler;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class TokenValidationHandler extends JwtValidationHandler {

    @Override
    protected Authentication process(String token) {
        if (token == null) {
            throw new JwtException("Invalid JWT token");
        }
        return null;
    }
}