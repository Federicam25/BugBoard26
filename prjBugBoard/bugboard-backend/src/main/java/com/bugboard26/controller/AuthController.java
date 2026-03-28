package com.bugboard26.controller;

import com.bugboard26.security.JwtUtil;
import com.bugboard26.service.AuthService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;
    private static final String USERNAME = "username";

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/login")
    public Map<String,String> login(@RequestBody Map<String,String> request){
        String username = request.get(USERNAME);
        String password = request.get("password");
        Map<String,String> user = null;
        try {
            user = authService.login(username,password);
        } catch (RuntimeException e) { // NOSONAR
            throw new RuntimeException("Invalid credentials"); // NOSONAR
        }

        if(user != null){
            String token = JwtUtil.generateToken(
                    user.get(USERNAME),
                    user.get("role")
            );
            return Map.of(
                    "token", token,
                    "role", user.get("role"),
                    USERNAME, user.get(USERNAME)
            );
        }
        throw new RuntimeException("Invalid credentials");// NOSONAR
    }
}