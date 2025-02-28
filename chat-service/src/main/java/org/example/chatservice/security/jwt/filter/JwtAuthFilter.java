package org.example.chatservice.security.jwt.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.chatservice.security.adapter.SecurityContextAdapter;
import org.example.chatservice.security.jwt.chain.JwtValidationChain;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends GenericFilterBean {
    private static final String TOKEN_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";
    private final SecurityContextAdapter securityContextAdapter;
    private final JwtValidationChain jwtValidationChain;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        Optional<String> token = getTokenFromRequest((HttpServletRequest) servletRequest);
        if (token.isPresent()) {
            Authentication authentication = jwtValidationChain
                    .build()
                    .handle(token.get());
            securityContextAdapter.setAuthentication(authentication);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private Optional<String> getTokenFromRequest(HttpServletRequest request) {
        final String tokenHeader = request.getHeader(TOKEN_HEADER);
        if (StringUtils.hasText(tokenHeader) && tokenHeader.startsWith(TOKEN_PREFIX)) {
            return Optional.of(tokenHeader.replace(TOKEN_PREFIX, ""));
        }
        return Optional.empty();
    }
}