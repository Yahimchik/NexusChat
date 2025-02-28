package org.example.chatservice.security.jwt.factory;

import org.springframework.security.core.Authentication;

public interface AuthenticationFactory {
    Authentication createAuthentication(String userId);
}
