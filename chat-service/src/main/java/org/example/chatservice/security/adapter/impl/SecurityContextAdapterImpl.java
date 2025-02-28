package org.example.chatservice.security.adapter.impl;

import org.example.chatservice.security.adapter.SecurityContextAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SecurityContextAdapterImpl implements SecurityContextAdapter {

    @Override
    public void setAuthentication(Authentication authentication) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public UUID getCurrentUserId() {
        var authentication = getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null) {
            throw new SecurityException("User is not authenticated");
        }
        String userId = ((User) authentication.getPrincipal()).getUsername();
        return UUID.fromString(userId);
    }
}

