package com.sparta.delivery.global.security;

import com.sparta.delivery.domain.user.enums.UserRole;
import com.sparta.delivery.global.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authorizationHeader =
                request.getHeader(JwtUtil.AUTHORIZATION_HEADER);

        if (StringUtils.hasText(authorizationHeader)
                && authorizationHeader.startsWith(JwtUtil.BEARER_PREFIX)) {

            String token = authorizationHeader.substring(
                    JwtUtil.BEARER_PREFIX.length()
            );

            if (jwtUtil.validateToken(token)) {

                String username = jwtUtil.getUsername(token);
                UserRole role = jwtUtil.getRole(token);

                AuthUser authUser = new AuthUser(
                        username,
                        role
                );

                SimpleGrantedAuthority authority =
                        new SimpleGrantedAuthority(
                                "ROLE_" + role.name()
                        );

                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(
                                authUser,
                                null,
                                List.of(authority)
                        );

                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}