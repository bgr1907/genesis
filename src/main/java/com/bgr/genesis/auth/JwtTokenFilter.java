package com.bgr.genesis.auth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final String bearerTokenText = "Bearer";
    private final String authorizationText = "Authorization";

    private final TokenManager tokenManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        /**
         * "Bearer 123xxx12132"
         */
        final String authHeader = request.getHeader(authorizationText);

        String username = null;
        String token = null;

        if (Objects.nonNull(authHeader) && authHeader.contains(bearerTokenText)) {
            token = authHeader.substring(7);
            try {
                username = tokenManager.getUsernameFromToken(token);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }

        if (Objects.nonNull(username) && Objects.isNull(
            SecurityContextHolder.getContext().getAuthentication())) {
            if (tokenManager.tokenValidate(token)) {
                UsernamePasswordAuthenticationToken usernamePassToken = new UsernamePasswordAuthenticationToken(username, null,
                    new ArrayList<>());
                usernamePassToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePassToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
