package org.example.gatewayservice.security;

import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.List;

public interface JwtTokenProvider {

    boolean validateAccessToken(String token);

    boolean validateRefreshToken(String token);

    String getLoginFromAccessToken(String token);

    Authentication getAuthentication(String token);

    LocalDateTime getExpirationDate(String token);

    List<String> getRolesFromAccessToken(String token);

    String getUserId(String token);
}
