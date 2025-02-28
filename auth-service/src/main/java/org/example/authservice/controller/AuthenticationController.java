package org.example.authservice.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.authservice.dto.auth.AuthenticationDto;
import org.example.authservice.dto.auth.JwtResponseDto;
import org.example.authservice.dto.auth.RefreshJwtRequestDto;
import org.example.authservice.service.AuthenticationService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public JwtResponseDto authenticate(@Valid @RequestBody AuthenticationDto authenticationDto) {
        return authenticationService.authenticate(authenticationDto);
    }

    @PostMapping("/token")
    public JwtResponseDto recreateToken(@Valid @RequestBody RefreshJwtRequestDto refreshJwtRequestDto) {
        return authenticationService.recreateToken(refreshJwtRequestDto);
    }

    @PostMapping("/logout")
    @SecurityRequirement(name = "Bearer Authentication")
    public void logout() {
        authenticationService.logout();
    }
}
