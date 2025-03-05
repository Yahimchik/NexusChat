package org.example.websocketsservice.security.impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.example.websocketsservice.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
@Slf4j
public class JwtTokenProviderImpl implements JwtTokenProvider {

    private final SecretKey jwtAccessSecret;

    public JwtTokenProviderImpl(
            @Value("${spring.application.security.jwt.secret.access}") String jwtAccessSecret) {
        this.jwtAccessSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessSecret));
    }

    @Override
    public String getUserIdFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtAccessSecret)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
