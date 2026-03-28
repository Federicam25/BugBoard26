package com.bugboard26.service;

import com.bugboard26.model.User;
import com.bugboard26.repository.UserRepository;
import com.bugboard26.utils.PasswordUtil;

import java.util.List;
public class UserService {
    private final UserRepository repository = new UserRepository();
    public int createUser(User user, String role)  {
        if (role == null || !role.equalsIgnoreCase("ADMIN")) {
            throw new RuntimeException("Unauthorized"); // NOSONAR
        }
        String hashed = PasswordUtil.hashPassword(user.getPassword());
        user.setPassword(hashed);
        return repository.save(user);

    }

    public List<String> getAllUsers(){
       return repository.findAllUsers();
    }
}