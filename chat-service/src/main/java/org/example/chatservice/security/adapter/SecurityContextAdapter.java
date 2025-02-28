package org.example.chatservice.security.adapter;

import org.springframework.security.core.Authentication;

import java.util.UUID;

public interface SecurityContextAdapter {
    void setAuthentication(Authentication authentication);

    Authentication getAuthentication();

    UUID getCurrentUserId();
}

