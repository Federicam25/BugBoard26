package com.bugboard26.service;

import com.bugboard26.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    UserRepository repository;

    @InjectMocks
    AuthService service;

    @Test
    void loginFallisce() {

        Map<String,String> result = service.login("wronguser@example.com", "wrongpass");
        assertTrue(result.isEmpty(), "Login fallita");
    }

    @Test
    void loginRiesce() {

        Map<String, String> mockResponse = Map.of(
                "email", "user@mail.com",
                "role", "USER"
        );

        when(repository.login("user@mail.com", "password"))
                .thenReturn(mockResponse);

        Map<String, String> result = service.login("user@mail.com", "password");

        assertNotNull(result);
        assertEquals("user@mail.com", result.get("email"));
        assertEquals("USER", result.get("role"));
    }
}