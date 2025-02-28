package org.example.gatewayservice.decorators;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.example.gatewayservice.security.JwtTokenProvider;
import org.example.gatewayservice.security.TokenCache;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Primary
@RequiredArgsConstructor
public class JwtTokenProviderDecorator implements JwtTokenProvider {

    private final JwtTokenProvider jwtTokenProvider;
    private final TokenCache tokenCache;

    @Override
    public boolean validateAccessToken(String token) {
        Boolean cachedValidation = tokenCache.getAccessTokenValidation(token);
        if (cachedValidation != null) {
            return cachedValidation;
        }

        boolean isValid = jwtTokenProvider.validateAccessToken(token);
        tokenCache.putAccessTokenValidation(token, isValid);
        return isValid;
    }

    @Override
    public boolean validateRefreshToken(String token) {
        Boolean cachedValidation = tokenCache.getRefreshTokenValidation(token);
        if (cachedValidation != null) {
            return cachedValidation;
        }

        boolean isValid = jwtTokenProvider.validateRefreshToken(token);
        tokenCache.putRefreshTokenValidation(token, isValid);
        return isValid;
    }

    @Override
    public String getLoginFromAccessToken(String token) {
        return jwtTokenProvider.getLoginFromAccessToken(token);
    }

    @Override
    public Authentication getAuthentication(String token) {
        return jwtTokenProvider.getAuthentication(token);
    }

    @Override
    public LocalDateTime getExpirationDate(String token) {
        return jwtTokenProvider.getExpirationDate(token);
    }

    @Override
    public List<String> getRolesFromAccessToken(String token) {
        return jwtTokenProvider.getRolesFromAccessToken(token);
    }

    @Override
    public String getUserId(String token) {
        return jwtTokenProvider.getUserId(token);
    }
}
