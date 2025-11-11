package com.kk.sso.config;

import java.io.IOException;

import com.kk.sso.entity.UserEntity;
import com.kk.sso.service.JwtService;
import com.kk.sso.service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader("access_token");
        final String authHeaderRefresh = request.getHeader("refresh_token");
        String jwt;
        String username;
        UserEntity uEntity = null;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7); // The part after "Bearer "
        log.info("step 1");
        username = jwtService.extractUsername(jwt);
        if(username == null) {
            username = jwtService.extractUsernameWithRefresh(authHeaderRefresh);
            if (username == null) {
                filterChain.doFilter(request, response);
                return;
            }
        }
        uEntity = this.userService.loadByUsername(username);
        jwt = jwtService.generateToken(uEntity);

        log.info("step 2");

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            uEntity = this.userService.loadByUsername(username);
            if (jwtService.isTokenValid(jwt, uEntity)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        uEntity,
                        null,
                        null);
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        log.info("step 3");

        filterChain.doFilter(request, response);
    }

}
