package org.example.chatservice.security.jwt;


public interface JwtTokenProvider {

    String getUserIdFromToken(String token);

}
