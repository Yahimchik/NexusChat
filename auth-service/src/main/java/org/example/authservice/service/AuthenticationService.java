package org.example.authservice.service;

import org.example.authservice.dto.auth.AuthenticationDto;
import org.example.authservice.dto.auth.JwtResponseDto;
import org.example.authservice.dto.auth.RefreshJwtRequestDto;

public interface AuthenticationService {

    JwtResponseDto authenticate(AuthenticationDto authenticationDto);

    JwtResponseDto recreateToken(RefreshJwtRequestDto refreshJwtRequestDto);

    void logout();
}
