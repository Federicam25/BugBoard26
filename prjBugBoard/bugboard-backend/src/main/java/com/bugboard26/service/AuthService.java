package com.bugboard26.service;
import com.bugboard26.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    private final UserRepository repository;
    public AuthService(UserRepository repository) {
        this.repository = repository;
    }

    public Map<String,String> login(String email, String password){
        Map<String,String> result = repository.login(email, password);
        if (result == null || result.isEmpty()) {
            return new HashMap<>();
        }
        return result;
    }
}