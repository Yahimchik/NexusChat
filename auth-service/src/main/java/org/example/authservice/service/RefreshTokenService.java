package org.example.authservice.service;

import org.example.authservice.model.User;
import org.example.authservice.model.token.RefreshToken;

public interface RefreshTokenService {
    void save(RefreshToken token);

    RefreshToken findByUserAndIp(User user, String ip);

    void updateRefreshToken(RefreshToken token, String newToken);

    void deleteByUserAndIp(User user, String ip);
}
