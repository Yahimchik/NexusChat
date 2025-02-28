package org.example.authservice.service.strategy.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.authservice.dto.auth.AuthenticationDto;
import org.example.authservice.dto.auth.JwtResponseDto;
import org.example.authservice.model.User;
import org.example.authservice.model.token.RefreshToken;
import org.example.authservice.repository.RefreshTokenRepository;
import org.example.authservice.repository.UserRepository;
import org.example.authservice.security.jwt.JwtTokenProvider;
import org.example.authservice.service.RefreshTokenService;
import org.example.authservice.service.UserService;
import org.example.authservice.service.exception.UserAuthenticationProcessingException;
import org.example.authservice.service.strategy.AuthenticationStrategy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.UUID;

@Component("passwordAuthStrategy")
@RequiredArgsConstructor
public class PasswordAuthenticationStrategy implements AuthenticationStrategy {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenService refreshTokenService;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public JwtResponseDto authenticate(AuthenticationDto authenticationDto) {
        try {
            UUID id = userRepository.findUserByEmail(authenticationDto.getEmail()).get().getId();
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            id,
                            authenticationDto.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user = userService.getUserByEmail(authenticationDto.getEmail());
            String accessToken = jwtTokenProvider.generateAccessToken(user);
            String refreshToken = jwtTokenProvider.generateRefreshToken(user);
            String ip = getUserIp();

            RefreshToken newToken = buildRefreshToken(user, refreshToken);

            if (refreshTokenRepository.findByUserAndIp(user, ip).isPresent()) {
                refreshTokenService.deleteByUserAndIp(user, ip);
            }
            refreshTokenService.save(newToken);
            return new JwtResponseDto(accessToken, refreshToken);
        } catch (AuthenticationException exception) {
            throw new UserAuthenticationProcessingException("Authentication error");
        }
    }

    private RefreshToken buildRefreshToken(User user, String refreshToken) {
        return RefreshToken.builder()
                .user(user)
                .token(refreshToken)
                .ipAddress(getUserIp())
                .expiryDate(jwtTokenProvider.getExpirationDate(refreshToken))
                .build();
    }

    private String getUserIp() {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder
                        .currentRequestAttributes())
                        .getRequest();
        return request.getRemoteAddr();
    }
}
