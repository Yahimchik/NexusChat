package org.example.chatservice.security.jwt.factory.impl;

import org.example.chatservice.security.jwt.factory.AuthenticationFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class JwtAuthenticationFactory implements AuthenticationFactory {
    @Override
    public Authentication createAuthentication(String userId) {
        User principal = new User(userId, "", new ArrayList<>());
        return new UsernamePasswordAuthenticationToken(principal, null, new ArrayList<>());
    }
}
