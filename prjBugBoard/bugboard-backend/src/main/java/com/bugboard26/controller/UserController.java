package com.bugboard26.controller;

import com.bugboard26.model.User;
import com.bugboard26.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService service = new UserService();
    @PostMapping
    public String createUser(@RequestBody User user, HttpServletRequest request) {
        String role = (String) request.getAttribute("role");
            int userId = service.createUser(user, role);
            if(userId == -1) return "KO";
        return "OK";
    }
    @GetMapping
    public List<String> getUsers(){
        return service.getAllUsers();

    }
}

