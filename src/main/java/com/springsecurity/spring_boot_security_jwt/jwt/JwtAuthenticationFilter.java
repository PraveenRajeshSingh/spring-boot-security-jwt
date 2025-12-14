package com.springsecurity.spring_boot_security_jwt.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        
        // Skip JWT filtering for Swagger UI and API docs paths
        String requestURI = request.getRequestURI();
        if (requestURI.contains("/v3/api-docs") || requestURI.contains("/swagger-ui")) {
            filterChain.doFilter(request, response);
            return;
        }
        
        // Filter logic will be implemented here
        String authHeader = request.getHeader("Authorization");

        // Enumeration< String > headerNames = request.getHeaderNames();
        //
        // while (headerNames.hasMoreElements()) {
        // log.info("Header Name :", headerNames.nextElement());
        //
        // }
        // log.info("Request Token :", authHeader);

        String username = null;

        String token = null;

        if (authHeader != null && authHeader.startsWith("Bearer")) {
            token = authHeader.substring(7);

            try {
                username = jwtTokenHelper.getUsernameFromToken(token);
            } catch (IllegalArgumentException e) {
                log.info("Unable to get JWT Token");

            } catch (ExpiredJwtException e) {
                log.info("Jwt Token has Expired");
            } catch (MalformedJwtException e) {
                log.info("Invalid JWT Token");

            }

        } else {
            log.info("JWT Token does not begin with Bearer..!");
        }
        // once we get the token , now validate

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtTokenHelper.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null,
                        userDetails.getAuthorities());

                usernamePasswordAuthenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            } else {
                log.info("Invalid Jwt Token..!");
            }
        } else {
            log.info("Username is null or context is null..!");
        }

        filterChain.doFilter(request, response);
    }
}