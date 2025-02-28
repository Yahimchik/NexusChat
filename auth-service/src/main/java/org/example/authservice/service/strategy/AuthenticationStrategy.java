package org.example.authservice.service.strategy;

import org.example.authservice.dto.auth.AuthenticationDto;
import org.example.authservice.dto.auth.JwtResponseDto;

public interface AuthenticationStrategy {

    JwtResponseDto authenticate(AuthenticationDto authenticationDto);
}
