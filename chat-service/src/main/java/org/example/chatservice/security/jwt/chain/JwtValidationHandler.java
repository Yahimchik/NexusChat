package org.example.chatservice.security.jwt.chain;

import org.springframework.security.core.Authentication;

public abstract class JwtValidationHandler {

    private JwtValidationHandler next;

    public JwtValidationHandler setNext(JwtValidationHandler next) {
        this.next = next;
        return next;
    }

    public Authentication handle(String token) {
        Authentication authentication = process(token);
        if (authentication == null && next != null) {
            return next.handle(token);
        }
        return authentication;
    }

    protected abstract Authentication process(String token);

}
