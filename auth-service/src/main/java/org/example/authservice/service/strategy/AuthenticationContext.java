package org.example.authservice.service.strategy;

import lombok.RequiredArgsConstructor;
import org.example.authservice.dto.auth.AuthenticationDto;
import org.example.authservice.dto.auth.JwtResponseDto;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class AuthenticationContext {

    private final Map<String, AuthenticationStrategy> strategies;

    public JwtResponseDto authenticate(String strategy, AuthenticationDto authenticationDto) {
        AuthenticationStrategy authenticationStrategy = strategies.get(strategy);
        if (authenticationStrategy == null) {
            throw new IllegalArgumentException("Unknown strategy: " + strategy);
        }
        return authenticationStrategy.authenticate(authenticationDto);
    }
}
