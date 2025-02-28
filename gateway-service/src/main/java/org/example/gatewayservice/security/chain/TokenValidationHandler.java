package org.example.gatewayservice.security.chain;

import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public abstract class TokenValidationHandler {

    private TokenValidationHandler next;

    public TokenValidationHandler setNext(TokenValidationHandler next) {
        this.next = next;
        return next;
    }

    public final Mono<Void> handle(ServerWebExchange exchange, String token) {
        if (!shouldProcessNext(token)) {
            return Mono.empty();
        }
        return process(exchange, token)
                .onErrorResume(Mono::error)
                .then(Mono.justOrEmpty(next))
                .flatMap(handler -> handler.handle(exchange, token));
    }

    protected abstract Mono<Void> process(ServerWebExchange exchange, String token);

    protected abstract boolean shouldProcessNext(String token);

}
