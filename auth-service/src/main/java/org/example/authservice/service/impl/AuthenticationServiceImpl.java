package org.example.authservice.service.impl;

import jakarta.persistence.EntityExistsException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.authservice.dto.auth.AuthenticationDto;
import org.example.authservice.dto.auth.JwtResponseDto;
import org.example.authservice.dto.auth.RefreshJwtRequestDto;
import org.example.authservice.model.User;
import org.example.authservice.model.token.RefreshToken;
import org.example.authservice.repository.RefreshTokenRepository;
import org.example.authservice.security.jwt.JwtTokenProvider;
import org.example.authservice.service.AuthenticationService;
import org.example.authservice.service.RefreshTokenService;
import org.example.authservice.service.UserService;
import org.example.authservice.service.exception.UserAuthenticationProcessingException;
import org.example.authservice.service.strategy.AuthenticationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationContext authenticationContext;

    @Override
    @Transactional
    public JwtResponseDto authenticate(AuthenticationDto authenticationDto) {
        return authenticationContext.authenticate("passwordAuthStrategy", authenticationDto);
    }

    @Override
    @Transactional
    public JwtResponseDto recreateToken(RefreshJwtRequestDto refreshJwtRequestDto) {
        String requestToken = refreshJwtRequestDto.getRefreshToken();
        if (jwtTokenProvider.validateRefreshToken(requestToken)) {
            String email = jwtTokenProvider.getLoginFromRefreshToken(requestToken);
            String ip = getUserIp();
            User user = userService.getUserByEmail(email);
            RefreshToken refreshToken = refreshTokenService.findByUserAndIp(user, ip);
            String tokenValue = refreshToken.getToken();

            if (Objects.nonNull(tokenValue) && tokenValue.equals(requestToken)) {
                String accessToken = jwtTokenProvider.generateAccessToken(user);
                String newRefreshToken = jwtTokenProvider.generateRefreshToken(user);
                refreshTokenService.updateRefreshToken(refreshToken, newRefreshToken);
                return new JwtResponseDto(accessToken, newRefreshToken);
            }
        }
        throw new EntityExistsException("JWT token is expired or invalid");
    }

    @Override
    @Transactional
    public void logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.nonNull(authentication)) {
            String ip = getUserIp();
            User user = userService.getUserByEmail(authentication.getName());
            refreshTokenRepository.deleteByUserAndIp(user, ip);
        }
    }

    private String getUserIp() {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder
                        .currentRequestAttributes())
                        .getRequest();
        return request.getRemoteAddr();
    }
}
