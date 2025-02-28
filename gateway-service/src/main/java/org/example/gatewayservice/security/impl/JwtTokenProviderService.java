package org.example.gatewayservice.security.impl;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.example.gatewayservice.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class JwtTokenProviderService implements JwtTokenProvider {

    private final SecretKey jwtAccessSecret;
    private final SecretKey jwtRefreshSecret;

    public JwtTokenProviderService(
            @Value("${spring.application.security.jwt.secret.access}") String jwtAccessSecret,
            @Value("${spring.application.security.jwt.secret.refresh}") String jwtRefreshSecret) {
        this.jwtAccessSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessSecret));
        this.jwtRefreshSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtRefreshSecret));
    }

    @Override
    public boolean validateAccessToken(String token) {
        return validateToken(token, jwtAccessSecret);
    }

    @Override
    public boolean validateRefreshToken(String token) {
        return validateToken(token, jwtRefreshSecret);
    }

    @Override
    public String getLoginFromAccessToken(String token) {
        Claims claims = getClaimsFromToken(token, jwtAccessSecret);
        return claims.getSubject();
    }

    @Override
    public Authentication getAuthentication(String token) {
        String login = getLoginFromAccessToken(token);
        return new UsernamePasswordAuthenticationToken(login, "", List.of());
    }

    @Override
    public LocalDateTime getExpirationDate(String token) {
        Claims claims = getClaimsFromToken(token, jwtAccessSecret);
        Date expiration = claims.getExpiration();
        return expiration
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    @Override
    public List<String> getRolesFromAccessToken(String token) {
        Claims claims = getClaimsFromToken(token, jwtAccessSecret);

        Object rolesObject = claims.get("role");
        if (rolesObject instanceof List<?>) {
            return ((List<?>) rolesObject).stream()
                    .map(String::valueOf)
                    .toList();
        }
        return List.of();
    }

    @Override
    public String getUserId(String token) {
        return getClaimsFromToken(token, jwtAccessSecret).getSubject();
    }

    private boolean validateToken(String token, SecretKey jwtAccessSecret) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(jwtAccessSecret)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            log.error("Token expired", expEx);
        } catch (UnsupportedJwtException unsEx) {
            log.error("Unsupported jwt", unsEx);
        } catch (MalformedJwtException mjEx) {
            log.error("Malformed jwt", mjEx);
        } catch (Exception e) {
            log.error("Invalid token", e);
        }
        return false;
    }

    private Claims getClaimsFromToken(@NonNull String token, @NonNull Key secret) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


}
