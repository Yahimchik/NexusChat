package org.example.gatewayservice.security;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class TokenCache {

    private final Cache<String, Boolean> accessTokenCache = Caffeine.newBuilder()
            .expireAfterWrite(Duration.ofMinutes(10)) // Токен будет жить 10 минут
            .maximumSize(1000) // Максимальное количество токенов в кеше
            .build();

    private final Cache<String, Boolean> refreshTokenCache = Caffeine.newBuilder()
            .expireAfterWrite(Duration.ofHours(1)) // Refresh токен дольше живет
            .maximumSize(500)
            .build();

    public Boolean getAccessTokenValidation(String token) {
        return accessTokenCache.getIfPresent(token);
    }

    public void putAccessTokenValidation(String token, boolean isValid) {
        accessTokenCache.put(token, isValid);
    }

    public Boolean getRefreshTokenValidation(String token) {
        return refreshTokenCache.getIfPresent(token);
    }

    public void putRefreshTokenValidation(String token, boolean isValid) {
        refreshTokenCache.put(token, isValid);
    }
}
