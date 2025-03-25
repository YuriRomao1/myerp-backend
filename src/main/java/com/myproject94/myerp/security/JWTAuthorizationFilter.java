package com.myproject94.myerp.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(JWTAuthorizationFilter.class);

    private final JWTUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager,
                                  JWTUtil jwtUtil,
                                  UserDetailsService userDetailsService) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String path = request.getRequestURI();
        logger.info("JWTAuthorizationFilter - Request URI: {}", path);

        // Verifica se o endpoint é para o Swagger e ignora a autenticação se for
        if (isPublicEndpoint(path)) {
            logger.info("Public endpoint detected: {}, bypassing JWT authorization.", path);
            chain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");
        logger.info("Authorization header: {}", header);

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            logger.info("Extracted token: {}", token);
            UsernamePasswordAuthenticationToken authToken = getAuthentication(token);
            if (authToken != null) {
                logger.info("Token is valid. Setting authentication for user: {}", authToken.getPrincipal());
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                logger.warn("Invalid token received.");
            }
        } else {
            logger.info("No Bearer token found in the request.");
        }
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        if (jwtUtil.tokenValido(token)) {
            String username = jwtUtil.getUsername(token);
            logger.info("Token valid. Username: {}", username);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
        }
        logger.warn("Token validation failed for token: {}", token);
        return null;
    }

    private boolean isPublicEndpoint(String path) {
        return path.startsWith("/swagger-ui/") ||
                path.startsWith("/v3/api-docs") ||
                path.startsWith("/swagger-resources") ||
                path.equals("/swagger-ui.html") ||
                path.startsWith("/h2-console") ||
                path.equals("/auth/login") ||
                path.equals("/usuarios/cadastro");
    }
}