package com.example.VeterinariaApp.security;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class JwtAuthFilter extends OncePerRequestFilter{

    
    private final JwtUtil jwtUtil;

    public JwtAuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = obtenerTokenDeHeader(request);
        String email = null;
        String rol = null;

        if (token != null && jwtUtil.validarToken(token)) {
            email = jwtUtil.obtenerEmail(token);
            rol = jwtUtil.obtenerRol(token);

            // Crear la autoridad con el prefijo "ROLE_"
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + rol);

            // Crear autenticación sin necesidad de usar org.springframework.security.core.userdetails.User
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(email, null, List.of(authority));

            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
        System.out.println("Autenticando usuario: " + email + " con rol: " + rol);
    }

    private String obtenerTokenDeHeader(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
    
}
