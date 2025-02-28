package org.example.chatservice.security.jwt.chain;

import lombok.RequiredArgsConstructor;
import org.example.chatservice.security.jwt.chain.tokenHandler.TokenAuthenticationHandler;
import org.example.chatservice.security.jwt.chain.tokenHandler.TokenPresenceHandler;
import org.example.chatservice.security.jwt.chain.tokenHandler.TokenValidationHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtValidationChain {
    private final TokenAuthenticationHandler tokenAuthenticationHandler;
    private final TokenPresenceHandler tokenPresenceHandler;
    private final TokenValidationHandler tokenValidationHandler;

    public JwtValidationHandler build() {
        tokenValidationHandler.setNext(tokenPresenceHandler);
        tokenPresenceHandler.setNext(tokenAuthenticationHandler);
        return tokenValidationHandler;
    }
}
