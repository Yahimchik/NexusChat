package org.example.gatewayservice.security.chain.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.gatewayservice.decorators.JwtTokenProviderDecorator;
import org.example.gatewayservice.security.chain.TokenValidationHandler;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccessControlHandler extends TokenValidationHandler {

    private final JwtTokenProviderDecorator jwtTokenProvider;
    private boolean isAccessAllowed = true;

    @Override
    protected Mono<Void> process(ServerWebExchange exchange, String token) {
        List<String> roles = jwtTokenProvider.getRolesFromAccessToken(token);
        String path = exchange.getRequest().getPath().value();

        if (isAccessDenied(path, roles)) {
            log.warn("Access denied for path: {}, roles: {}", path, roles);
            isAccessAllowed = false;
            return Mono.error(new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied"));
        }
        return Mono.empty();
    }


    @Override
    protected boolean shouldProcessNext(String token) {
        return isAccessAllowed;
    }

    private boolean isAccessDenied(String path, List<String> roles) {
        if (path.startsWith("/api/v1/auth") && !roles.contains("USER")) {
            return true;
        }
        return path.startsWith("/api/v1/users") && !roles.contains("USER");
    }
}
