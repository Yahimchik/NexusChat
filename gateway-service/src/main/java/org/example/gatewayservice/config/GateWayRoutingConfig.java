package org.example.gatewayservice.config;

import org.example.gatewayservice.security.filter.JwtAuthenticationFilter;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GateWayRoutingConfig {
    @Bean
    public GlobalFilter jwtFilter(JwtAuthenticationFilter jwtAuthenticationFilter) {
        return jwtAuthenticationFilter::filter;
    }
}