package com.bugboard26.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
public class JwtFilter implements Filter {
    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String path = req.getRequestURI();
        // Login non richiede token
        if(path.contains("/api/login")){
            chain.doFilter(request,response);
            return;
        }
        if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
            chain.doFilter(request, response);
            return;
        }
        String authHeader = req.getHeader("Authorization");
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        try{
            String token = authHeader.substring(7);
            String username = JwtUtil.validateToken(token);
            String role = JwtUtil.getRole(token);
            req.setAttribute("role", role);
            req.setAttribute("username", username);
        }catch(Exception e){
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        chain.doFilter(request,response);
    }
}