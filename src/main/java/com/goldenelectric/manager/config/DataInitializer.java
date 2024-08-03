package com.goldenelectric.manager.config;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

import com.goldenelectric.manager.models.User;
import com.goldenelectric.manager.repositories.UserRepository;

import jakarta.annotation.PostConstruct;

@Component
public class DataInitializer {
    private final UserRepository userRepository;

    public DataInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void init() {
        createAdminUser();
    }

    private void createAdminUser() {
        String adminEmail = "admin@admin.com";
        String adminPassword = "admin";

        User adminUser = userRepository.findByEmail(adminEmail);
        if (adminUser == null) {
            User user = new User();
            user.setEmail(adminEmail);
            user.setFirstName("admin");
            user.setLastName("admin");
            user.setPassword(BCrypt.hashpw(adminPassword, BCrypt.gensalt()));;
            user.setRole(User.Role.ADMIN);

            userRepository.save(user);
        }
    }
}
