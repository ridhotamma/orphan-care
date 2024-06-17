package org.orphancare.dashboard.config;

import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.entity.RoleType;
import org.orphancare.dashboard.entity.User;
import org.orphancare.dashboard.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializerConfig implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        String email = "admin@psaa.com";
        String password = "Admin123";
        String username = "adminpsaa";
        boolean active = true;
        Set<RoleType> roles = new HashSet<>();
        roles.add(RoleType.ROLE_ADMIN);
        roles.add(RoleType.ROLE_USER);

        Optional<User> existingUserByEmail = userRepository.findByEmail(email);
        Optional<User> existingUserByUsername = userRepository.findByUsername(username);

        if (existingUserByEmail.isEmpty() && existingUserByUsername.isEmpty()) {
            User user = new User();
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));
            user.setUsername(username);
            user.setActive(active);
            user.setRoles(roles);

            userRepository.save(user);
            System.out.println("Admin user created successfully");
        } else {
            System.out.println("Admin user already exists");
        }
    }
}
