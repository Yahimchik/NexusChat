package org.example.chatservice.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UuidGenerator {

    @Bean
    public UUID generate() {
        return UUID.randomUUID();
    }
}
