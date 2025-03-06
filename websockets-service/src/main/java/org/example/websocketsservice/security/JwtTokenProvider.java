package org.example.websocketsservice.security;


public interface JwtTokenProvider {

    String getUserIdFromToken(String token);

}
