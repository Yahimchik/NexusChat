package org.example.gatewayservice.security.chain;

import lombok.RequiredArgsConstructor;
import org.example.gatewayservice.security.chain.impl.AccessControlHandler;
import org.example.gatewayservice.security.chain.impl.AccessTokenHandler;
import org.example.gatewayservice.security.chain.impl.RefreshTokenHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenValidationChainBuilder {
    private final AccessTokenHandler accessTokenHandler;
    private final AccessControlHandler accessControlHandler;
    private final RefreshTokenHandler refreshTokenHandler;

    public TokenValidationHandler build() {
        accessControlHandler.setNext(accessTokenHandler);
        accessTokenHandler.setNext(refreshTokenHandler);
        return accessControlHandler;
    }
}
