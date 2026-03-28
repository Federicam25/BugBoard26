package com.bugboard26.service;

import com.bugboard26.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService service;

    @Test
    void createUserFallisceSeNonAdmin() {
        User user = new User("test@mail.com", "password", "USER");

        Exception ex = assertThrows(Exception.class, () ->
                service.createUser(user, "USER")
        );
        assertEquals("Unauthorized", ex.getMessage());
    }

    @Test
    void createUserFallisceSeRuoloNull() {
        User user = new User("test@mail.com", "password", "USER");

        Exception ex = assertThrows(Exception.class, () ->
                service.createUser(user, null)
        );
        assertEquals("Unauthorized", ex.getMessage());
    }
}
