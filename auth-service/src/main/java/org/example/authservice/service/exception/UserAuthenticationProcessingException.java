package org.example.authservice.service.exception;

public class UserAuthenticationProcessingException extends RuntimeException {
    public UserAuthenticationProcessingException(String message) {
        super(message);
    }
}
