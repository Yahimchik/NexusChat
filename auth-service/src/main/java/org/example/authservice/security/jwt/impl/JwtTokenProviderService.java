package org.example.authservice.security.jwt.impl;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.example.authservice.model.User;
import org.example.authservice.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class JwtTokenProviderService implements JwtTokenProvider {

    private final SecretKey jwtAccessSecret;
    private final SecretKey jwtRefreshSecret;
    private final UserDetailsService userDetailsService;

    public JwtTokenProviderService(
            @Value("${spring.application.security.jwt.secret.access}") String jwtAccessSecret,
            @Value("${spring.application.security.jwt.secret.refresh}") String jwtRefreshSecret,
            @Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService) {
        this.jwtAccessSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessSecret));
        this.jwtRefreshSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtRefreshSecret));
        this.userDetailsService = userDetailsService;
    }


    @Override
    public String generateAccessToken(User user) {
        System.out.println(user);
        Claims claims = Jwts.claims().setSubject(user.getId().toString());
        claims.put("role", List.of(user.getRole()));
        claims.put("status", user.getStatus());

        final LocalDateTime now = LocalDateTime.now();
        final Instant accessExpirationInstant = now
                .plusMinutes(15)
                .atZone(ZoneId.systemDefault())
                .toInstant();
        final Date accessExpiration = Date.from(accessExpirationInstant);

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(accessExpiration)
                .signWith(jwtAccessSecret)
                .compact();
    }

    @Override
    public String generateRefreshToken(User user) {
        LocalDateTime now = LocalDateTime.now();
        Instant refreshExpirationInstant = now
                .plusDays(30)
                .atZone(ZoneId.systemDefault())
                .toInstant();

        Date refreshExpiration = Date.from(refreshExpirationInstant);

        return Jwts.builder()
                .setSubject(user.getId().toString())
                .setExpiration(refreshExpiration)
                .signWith(jwtRefreshSecret)
                .compact();
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
    public Claims getAccessClaims(String token) {
        return getClaimsFromToken(token, jwtAccessSecret);
    }

    @Override
    public Claims getRefreshClaims(String token) {
        return getClaimsFromToken(token, jwtRefreshSecret);
    }

    @Override
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getLoginFromAccessToken(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    @Override
    public String getLoginFromAccessToken(String token) {
        Claims claims = getAccessClaims(token);
        return claims.getSubject();
    }

    @Override
    public String getLoginFromRefreshToken(String token) {
        Claims claims = getRefreshClaims(token);
        return claims.getSubject();
    }

    @Override
    public LocalDateTime getExpirationDate(String token) {
        Claims claims = getRefreshClaims(token);
        Date expiration = claims.getExpiration();
        return expiration
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    private boolean validateToken(@NonNull String token, @NonNull Key secret) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secret)
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
            log.error("invalid token", e);
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
