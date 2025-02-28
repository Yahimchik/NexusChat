package org.example.gatewayservice.security.chain.impl;

import lombok.RequiredArgsConstructor;
import org.example.gatewayservice.decorators.JwtTokenProviderDecorator;
import org.example.gatewayservice.security.chain.TokenValidationHandler;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RefreshTokenHandler extends TokenValidationHandler {

    private final JwtTokenProviderDecorator jwtTokenProvider;

    @Override
    protected Mono<Void> process(ServerWebExchange exchange, String token) {
        if (!jwtTokenProvider.validateRefreshToken(token)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        return Mono.empty();
    }

    @Override
    protected boolean shouldProcessNext(String token) {
        return !jwtTokenProvider.validateRefreshToken(token);
    }
}
