package org.example.chatservice.security.jwt.chain.tokenHandler;

import lombok.RequiredArgsConstructor;
import org.example.chatservice.security.jwt.JwtTokenProvider;
import org.example.chatservice.security.jwt.chain.JwtValidationHandler;
import org.example.chatservice.security.jwt.factory.AuthenticationFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenAuthenticationHandler extends JwtValidationHandler {
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationFactory authenticationFactory;

    @Override
    protected Authentication process(String token) {
        String userId = jwtTokenProvider.getUserIdFromToken(token);
        return authenticationFactory.createAuthentication(userId);
    }
}