package org.example.authservice.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.authservice.model.User;
import org.example.authservice.model.token.RefreshToken;
import org.example.authservice.repository.RefreshTokenRepository;
import org.example.authservice.security.jwt.JwtTokenProvider;
import org.example.authservice.service.RefreshTokenService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void save(RefreshToken token) {
        refreshTokenRepository.save(token);
    }

    @Override
    public RefreshToken findByUserAndIp(User user, String ip) {
        return refreshTokenRepository.findByUserAndIp(user, ip)
                .orElseThrow(() -> new EntityNotFoundException("Token not found"));
    }

    @Override
    public void updateRefreshToken(RefreshToken token, String newToken) {
        token.setToken(newToken);
        token.setExpiryDate(jwtTokenProvider.getExpirationDate(newToken));
        refreshTokenRepository.save(token);
    }

    @Override
    public void deleteByUserAndIp(User user, String ip) {
        refreshTokenRepository.deleteByUserAndIp(user, ip);
    }
}
