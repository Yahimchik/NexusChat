package org.example.gatewayservice.security.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.gatewayservice.security.JwtTokenProvider;
import org.example.gatewayservice.security.chain.TokenValidationChainBuilder;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter implements GatewayFilter {

    private final TokenValidationChainBuilder tokenValidationChainBuilder;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        Optional<String> token = getTokenFromRequest(exchange);
        return token.map(jwt ->
                        tokenValidationChainBuilder.build()
                                .handle(exchange, jwt)
                                .then(chain.filter(exchange)))
                .orElseGet(() -> chain.filter(exchange));
    }

    private Optional<String> getTokenFromRequest(ServerWebExchange exchange) {
        final String tokenHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (StringUtils.hasText(tokenHeader) && tokenHeader.startsWith("Bearer ")) {
            return Optional.of(tokenHeader.replace("Bearer ", ""));
        }
        return Optional.empty();
    }
}
