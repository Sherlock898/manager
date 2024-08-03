package com.goldenelectric.manager.services;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.goldenelectric.manager.models.User;
import com.goldenelectric.manager.repositories.UserRepository;

import jakarta.validation.Valid;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Get all users
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    // Get user by id
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // Get user by email
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Create user
    public User registerUser(@Valid User user) {
        String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashed);
        return userRepository.save(user);
    }

    // Update user
    public User update(User user) {
        return userRepository.save(user);
    }

    // Delete user
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    // Check if the user exists
    public boolean userExists(Long id) {
        return userRepository.existsById(id);
    }

    // Authenticate user
    public boolean authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return false;
        }
        return BCrypt.checkpw(password, user.getPassword());
    }

}
